package ar.edu.ort.vora.ui.workout

import ar.edu.ort.vora.domain.model.Workout
import ar.edu.ort.vora.ui.components.UiText

data class WorkoutUiState(
    val workouts: List<Workout> = emptyList(),
    val selectedWorkout: Workout? = null,
    val isLoading: Boolean = false,
    val isLoadingDetail: Boolean = false,
    val error: UiText? = null,
    val detailError: UiText? = null
)