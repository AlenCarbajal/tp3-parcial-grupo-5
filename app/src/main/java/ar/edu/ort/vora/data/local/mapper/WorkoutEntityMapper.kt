package ar.edu.ort.vora.data.local.mapper

import ar.edu.ort.vora.data.local.entity.ExerciseEntity
import ar.edu.ort.vora.data.local.entity.WorkoutEntity
import ar.edu.ort.vora.domain.model.Exercise
import ar.edu.ort.vora.domain.model.Workout

fun Workout.toEntity(): WorkoutEntity =
    WorkoutEntity(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exerciseCount,
        description = description
    )

fun WorkoutEntity.toDomain(
    exercises: List<Exercise> = emptyList()
): Workout =
    Workout(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exerciseCount,
        description = description,
        exercises = exercises
    )

fun Exercise.toEntity(): ExerciseEntity =
    ExerciseEntity(
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

fun ExerciseEntity.toDomain(): Exercise =
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