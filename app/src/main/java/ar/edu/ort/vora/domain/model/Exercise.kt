package ar.edu.ort.vora.domain.model

data class Exercise(
    val id: String,
    val workoutId: String,
    val name: String,
    val muscleGroup: String,
    val sets: Int,
    val reps: Int,
    val restSeconds: Int,
    val instructions: String,
    val tip: String
)