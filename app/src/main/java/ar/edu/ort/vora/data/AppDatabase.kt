package ar.edu.ort.vora.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.ort.vora.data.dao.MealDao
import ar.edu.ort.vora.data.entities.MealEntity


class AppDatabase {

    @Database(entities = [MealEntity.comida::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun mealDao(): MealDao
    }
}

