package ar.edu.ort.vora.di

import android.content.Context
import androidx.room.Room
import ar.edu.ort.vora.data.local.AppDatabase
import ar.edu.ort.vora.data.local.dao.WorkoutDao
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(
        database: AppDatabase
    ): WorkoutDao {
        return database.workoutDao()
    }
}