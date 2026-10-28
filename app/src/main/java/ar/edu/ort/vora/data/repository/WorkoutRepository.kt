package ar.edu.ort.vora.data.repository

import ar.edu.ort.vora.data.local.dao.WorkoutDao
import ar.edu.ort.vora.data.local.mapper.toDomain
import ar.edu.ort.vora.data.local.mapper.toEntity
import ar.edu.ort.vora.data.mapper.toDomain
import ar.edu.ort.vora.data.remote.ApiService
import ar.edu.ort.vora.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val apiService: ApiService,
    private val workoutDao: WorkoutDao
) {

    fun observeWorkouts(): Flow<List<Workout>> =
        workoutDao.observeWorkouts().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }

    suspend fun refreshWorkouts() {
        val response = apiService.getWorkouts()

        val workouts = response.data.map { dto ->
            dto.toDomain().toEntity()
        }

        workoutDao.replaceWorkouts(workouts)
    }

    suspend fun getWorkoutDetail(workoutId: String): Workout {
        val response = apiService.getWorkoutById(workoutId)
        val workout = response.data.toDomain()

        workoutDao.insertWorkouts(
            listOf(workout.toEntity())
        )

        workoutDao.replaceExercises(
            workoutId = workout.id,
            exercises = workout.exercises.map { exercise ->
                exercise.toEntity()
            }
        )

        return workout
    }

    suspend fun getCachedWorkout(workoutId: String): Workout? {
        val workoutEntity = workoutDao.getWorkoutById(workoutId)
            ?: return null

        val exercises = workoutDao
            .getExercisesByWorkoutId(workoutId)
            .map { entity -> entity.toDomain() }

        return workoutEntity.toDomain(exercises)
    }
}