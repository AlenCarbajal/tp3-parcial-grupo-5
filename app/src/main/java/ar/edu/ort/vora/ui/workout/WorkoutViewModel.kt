package ar.edu.ort.vora.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.ort.vora.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(error = "No se pudieron actualizar los entrenamientos.")
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
            } catch (exception: Exception) {
                val cachedWorkout = repository.getCachedWorkout(workoutId)

                _uiState.update {
                    it.copy(
                        selectedWorkout = cachedWorkout,
                        detailError = if (cachedWorkout == null) {
                            "No se pudo cargar el entrenamiento."
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
}