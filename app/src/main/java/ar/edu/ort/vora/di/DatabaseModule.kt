package ar.edu.ort.vora.di

import android.content.Context
import androidx.room.Room
import ar.edu.ort.vora.data.local.AppDatabase
import ar.edu.ort.vora.data.local.dao.WorkoutDao
import ar.edu.ort.vora.data.local.dao.MealDao
import ar.edu.ort.vora.data.local.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "vora_coach_database"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(
        database: AppDatabase
    ): WorkoutDao {
        return database.workoutDao()
    }

    @Provides
    @Singleton
    fun provideMealDao(
        database: AppDatabase
    ): MealDao {
        return database.mealDao()
    }
}

