package ar.edu.ort.vora.ui.nutrition

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.vora.data.repository.MealRepository
import ar.edu.ort.vora.domain.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface MealDetailUiState {
    data object Loading : MealDetailUiState
    data class Found(val meal: Meal) : MealDetailUiState
    data object NotFound : MealDetailUiState
}

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: MealRepository,
) : ViewModel() {

    private val mealId: Int = checkNotNull(savedStateHandle["mealId"])

    val uiState: StateFlow<MealDetailUiState> = repository.getMealById(mealId)
        .map<Meal?, MealDetailUiState> { if (it == null) MealDetailUiState.NotFound else MealDetailUiState.Found(it) }
        .catch { emit(MealDetailUiState.NotFound) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MealDetailUiState.Loading)

}
