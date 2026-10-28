package ar.edu.ort.vora.ui.workout

import ar.edu.ort.vora.domain.model.Workout

data class WorkoutUiState(
    val workouts: List<Workout> = emptyList(),
    val selectedWorkout: Workout? = null,
    val isLoading: Boolean = false,
    val isLoadingDetail: Boolean = false,
    val error: String? = null,
    val detailError: String? = null
)