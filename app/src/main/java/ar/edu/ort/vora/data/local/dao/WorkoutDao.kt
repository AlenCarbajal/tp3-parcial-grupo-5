package ar.edu.ort.vora.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ar.edu.ort.vora.data.local.entity.ExerciseEntity
import ar.edu.ort.vora.data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workouts ORDER BY name ASC")
    fun observeWorkouts(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: String): WorkoutEntity?

    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    suspend fun getExercisesByWorkoutId(
        workoutId: String
    ): List<ExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkouts(workouts: List<WorkoutEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises WHERE workoutId = :workoutId")
    suspend fun deleteExercisesByWorkoutId(workoutId: String)

    @Query("DELETE FROM workouts")
    suspend fun clearWorkouts()

    @Transaction
    suspend fun replaceWorkouts(workouts: List<WorkoutEntity>) {
        clearWorkouts()
        insertWorkouts(workouts)
    }

    @Transaction
    suspend fun replaceExercises(
        workoutId: String,
        exercises: List<ExerciseEntity>
    ) {
        deleteExercisesByWorkoutId(workoutId)
        if (exercises.isNotEmpty()) {
            insertExercises(exercises)
        }
    }
}