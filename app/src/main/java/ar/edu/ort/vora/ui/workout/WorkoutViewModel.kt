package ar.edu.ort.vora.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.vora.R
import ar.edu.ort.vora.data.repository.WorkoutRepository
import ar.edu.ort.vora.ui.components.UiText
import com.google.gson.JsonParseException
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.google.gson.stream.MalformedJsonException
import java.io.EOFException

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeWorkouts()
        refreshWorkouts()
    }

    private fun observeWorkouts() {
        viewModelScope.launch {
            repository.observeWorkouts().collect { workouts ->
                _uiState.update { state ->
                    state.copy(workouts = workouts)
                }
            }
        }
    }

    fun refreshWorkouts() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                repository.refreshWorkouts()
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                android.util.Log.e(
                    "WorkoutViewModel",
                    "Error al actualizar entrenamientos",
                    exception
                )

                _uiState.update {
                    it.copy(error = exception.toUserMessage())
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun loadWorkoutDetail(workoutId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoadingDetail = true, detailError = null)
            }

            try {
                val workout = repository.getWorkoutDetail(workoutId)
                _uiState.update {
                    it.copy(selectedWorkout = workout)
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                android.util.Log.e(
                    "WorkoutViewModel",
                    "Error al cargar el detalle $workoutId",
                    exception
                )

                // Solo sirve como respaldo si tiene ejercicios: un workout
                // cacheado solo con el resumen del listado no alcanza.
                val cachedWorkout = repository.getCachedWorkout(workoutId)
                    ?.takeIf { it.exercises.isNotEmpty() }

                _uiState.update {
                    it.copy(
                        selectedWorkout = cachedWorkout,
                        detailError = if (cachedWorkout == null) {
                            // RF02: detalle nunca descargado y sin servidor
                            if (exception.isConnectionProblem()) {
                                UiText(R.string.workout_error_offline_detail)
                            } else {
                                exception.toUserMessage()
                            }
                        } else {
                            null
                        }
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(isLoadingDetail = false)
                }
            }
        }
    }

    fun clearSelectedWorkout() {
        _uiState.update {
            it.copy(selectedWorkout = null, detailError = null)
        }
    }

    /** Problema de red (sin conexión, timeout), no una respuesta rota del servidor. */
    private fun Throwable.isConnectionProblem(): Boolean =
        this is IOException && this !is MalformedJsonException && this !is EOFException

    /** Traduce excepciones técnicas a un mensaje que pueda leer el usuario. */
    private fun Throwable.toUserMessage(): UiText = when (this) {
        is HttpException ->
            UiText(R.string.common_error_server, listOf(code()))
        // Antes que IOException: estos son subtipos de IOException pero indican
        // que llegó una respuesta con JSON roto o incompleto, no un problema de red.
        is JsonParseException, is MalformedJsonException, is EOFException ->
            UiText(R.string.common_error_bad_data)
        is IOException ->
            UiText(R.string.common_error_network)
        else ->
            UiText(R.string.common_error_unexpected)
    }
}