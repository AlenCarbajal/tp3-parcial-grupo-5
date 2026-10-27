package ar.edu.ort.vora.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.ort.vora.data.local.dao.WorkoutDao
import ar.edu.ort.vora.data.local.dao.MealDao
import ar.edu.ort.vora.data.local.entity.MealEntity
import ar.edu.ort.vora.data.local.entity.ExerciseEntity
import ar.edu.ort.vora.data.local.entity.WorkoutEntity

@Database(
    entities = [
        MealEntity::class,
        WorkoutEntity::class,
        ExerciseEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun workoutDao(): WorkoutDao
}