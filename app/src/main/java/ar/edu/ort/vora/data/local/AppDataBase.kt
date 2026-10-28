package ar.edu.ort.vora.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.ort.vora.data.local.dao.WorkoutDao
import ar.edu.ort.vora.data.local.entity.ExerciseEntity
import ar.edu.ort.vora.data.local.entity.WorkoutEntity

@Database(
    entities = [
        WorkoutEntity::class,
        ExerciseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
}