package ar.edu.ort.vora.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

    // IGNORE (y no REPLACE): REPLACE borra la fila vieja y, por el
    // ON DELETE CASCADE de exercises, se llevaría los ejercicios cacheados.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkoutIfAbsent(workout: WorkoutEntity): Long

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    // Actualiza solo los datos del listado y deja intacta la description,
    // que el endpoint de lista no devuelve y solo viene en el detalle.
    @Query(
        """
        UPDATE workouts
        SET name = :name,
            level = :level,
            durationMinutes = :durationMinutes,
            exerciseCount = :exerciseCount
        WHERE id = :id
        """
    )
    suspend fun updateWorkoutSummary(
        id: String,
        name: String,
        level: String,
        durationMinutes: Int,
        exerciseCount: Int
    )

    @Query("DELETE FROM workouts WHERE id NOT IN (:keepIds)")
    suspend fun deleteWorkoutsNotIn(keepIds: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises WHERE workoutId = :workoutId")
    suspend fun deleteExercisesByWorkoutId(workoutId: String)

    /**
     * Sincroniza el listado con lo que devolvió el servidor sin borrar
     * lo que ya estaba cacheado: inserta los nuevos, actualiza el resumen
     * de los existentes y elimina solo los que el servidor ya no tiene.
     */
    @Transaction
    suspend fun syncWorkoutSummaries(workouts: List<WorkoutEntity>) {
        workouts.forEach { workout ->
            val rowId = insertWorkoutIfAbsent(workout)
            if (rowId == -1L) {
                updateWorkoutSummary(
                    id = workout.id,
                    name = workout.name,
                    level = workout.level,
                    durationMinutes = workout.durationMinutes,
                    exerciseCount = workout.exerciseCount
                )
            }
        }
        deleteWorkoutsNotIn(workouts.map { it.id })
    }

    /** Guarda el detalle completo (con description) y sus ejercicios. */
    @Transaction
    suspend fun saveWorkoutDetail(
        workout: WorkoutEntity,
        exercises: List<ExerciseEntity>
    ) {
        if (insertWorkoutIfAbsent(workout) == -1L) {
            updateWorkout(workout)
        }
        deleteExercisesByWorkoutId(workout.id)
        if (exercises.isNotEmpty()) {
            insertExercises(exercises)
        }
    }
}
