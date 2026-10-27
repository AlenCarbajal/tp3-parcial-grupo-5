package ar.edu.ort.vora.ui.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.vora.data.repository.MealRepository
import ar.edu.ort.vora.domain.model.Meal
import ar.edu.ort.vora.domain.model.toDailyTotals
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(todayIso())
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    // Subir este contador vuelve a armar la consulta (botón Reintentar)
    private val retryTrigger = MutableStateFlow(0)

    // Lista y total diario salen del mismo Flow de Room: cambian solo al insertar o borrar.
    // WhileSubscribed(5s) hace que al rotar no se vuelva a consultar.
    val uiState: StateFlow<NutritionUIState> =
        combine(_selectedDate, retryTrigger) { date, _ -> date }
            .flatMapLatest { date ->
                repository.getMealsByDate(date)
                    .map<List<Meal>, NutritionUIState> { meals ->
                        if (meals.isEmpty()) {
                            NutritionUIState.Empty
                        } else {
                            NutritionUIState.Success(meals, meals.toDailyTotals())
                        }
                    }
                    .onStart { emit(NutritionUIState.Loading) }
                    .catch { emit(NutritionUIState.Error("No se pudieron cargar las comidas.")) }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NutritionUIState.Loading
            )

    // Errores de guardar o borrar (no reemplazan la lista que ya se ve)
    private val _actionError = MutableStateFlow<String?>(null)
    val actionError: StateFlow<String?> = _actionError.asStateFlow()

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    fun retry() {
        retryTrigger.update { it + 1 }
    }

    fun addMeal(meal: Meal) {
        // Defensa mínima: el formulario (T32) valida primero, pero a Room nunca llega un dato inválido
        val problem = validate(meal)
        if (problem != null) {
            _actionError.value = problem
            return
        }

        viewModelScope.launch {
            try {
                repository.addMeal(meal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _actionError.value = "No se pudo guardar la comida."
            }
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            try {
                repository.deleteMeal(meal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _actionError.value = "No se pudo borrar la comida."
            }
        }
    }

    fun clearActionError() {
        _actionError.value = null
    }

    // RF03: nombre no vacío y valores >= 0
    private fun validate(meal: Meal): String? = when {
        meal.name.isBlank() -> "El nombre no puede estar vacío."
        meal.kcal < 0 || meal.protein < 0 || meal.carbs < 0 || meal.fat < 0 ->
            "Los valores no pueden ser negativos."
        else -> null
    }
}

// minSdk 24: java.time (LocalDate) pide API 26, por eso se usa SimpleDateFormat
private fun todayIso(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())