package ar.edu.ort.vora.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WorkoutResponseDto(
    @SerializedName("data")
    val data: List<WorkoutDto>
)

data class WorkoutDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("durationMinutes")
    val durationMinutes: Int,
    @SerializedName("exerciseCount")
    val exerciseCount: Int
)

data class WorkoutDetailResponseDto(
    @SerializedName("data")
    val data: WorkoutDetailDto
)

data class WorkoutDetailDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("durationMinutes")
    val durationMinutes: Int,
    @SerializedName("exerciseCount")
    val exerciseCount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("exercises")
    val exercises: List<ExerciseDto>
)

data class ExerciseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("muscleGroup")
    val muscleGroup: String,
    @SerializedName("sets")
    val sets: Int,
    @SerializedName("reps")
    val reps: Int,
    @SerializedName("restSeconds")
    val restSeconds: Int,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("tip")
    val tip: String
)