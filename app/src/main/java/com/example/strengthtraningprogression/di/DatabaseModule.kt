package com.example.strengthtraningprogression.di

import android.content.Context
import androidx.room.Room
import com.example.strengthtraningprogression.data.local.AppDatabase
import com.example.strengthtraningprogression.data.local.dao.ExerciseDao
import com.example.strengthtraningprogression.data.local.dao.SetDao
import com.example.strengthtraningprogression.data.local.dao.WorkoutDao
import com.example.strengthtraningprogression.data.local.dao.WorkoutExerciseDao
import com.example.strengthtraningprogression.data.repository.ExerciseRepository
import com.example.strengthtraningprogression.data.repository.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "workout-db"
        ).build()
    }

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao {
        return db.exerciseDao()
    }

    @Provides
    fun provideWorkoutDao(db: AppDatabase): WorkoutDao {
        return db.workoutDao()
    }

    @Provides
    fun provideWorkoutExerciseDao(db: AppDatabase): WorkoutExerciseDao {
        return db.workoutExerciseDao()
    }

    @Provides
    fun provideSetDao(db: AppDatabase): SetDao {
        return db.setDao()
    }

    @Provides
    fun provideExerciseRepository(
        dao: ExerciseDao
    ): ExerciseRepository {
        return ExerciseRepository(dao)
    }

    @Provides
    fun provideWorkoutRepository(
        workoutDao: WorkoutDao,
        workoutExerciseDao: WorkoutExerciseDao,
        setDao: SetDao,
        exerciseDao: ExerciseDao
    ): WorkoutRepository {
        return WorkoutRepository(
            workoutDao,
            workoutExerciseDao,
            setDao,
            exerciseDao
        )
    }
}