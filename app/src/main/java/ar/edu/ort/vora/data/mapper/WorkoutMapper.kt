package ar.edu.ort.vora.data.mapper

import ar.edu.ort.vora.data.remote.dto.WorkoutDto
import ar.edu.ort.vora.data.remote.dto.WorkoutDetailDto
import ar.edu.ort.vora.data.remote.dto.ExerciseDto
import ar.edu.ort.vora.domain.model.Workout
import ar.edu.ort.vora.domain.model.Exercise

fun WorkoutDto.toDomain(): Workout =
    Workout(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exerciseCount
    )

fun WorkoutDetailDto.toDomain(): Workout =
    Workout(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exerciseCount,
        description = description,
        exercises = exercises.map { it.toDomain(workoutId = id) }
    )

fun ExerciseDto.toDomain(workoutId: String): Exercise =
    Exercise(
        id = id,
        workoutId = workoutId,
        name = name,
        muscleGroup = muscleGroup,
        sets = sets,
        reps = reps,
        restSeconds = restSeconds,
        instructions = instructions,
        tip = tip
    )