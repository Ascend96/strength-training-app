package com.example.strengthtraningprogression.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.strengthtraningprogression.data.local.dao.ExerciseDao
import com.example.strengthtraningprogression.data.local.dao.SetDao
import com.example.strengthtraningprogression.data.local.dao.WorkoutDao
import com.example.strengthtraningprogression.data.local.dao.WorkoutExerciseDao
import com.example.strengthtraningprogression.data.local.entity.*

@Database(
    entities = [
        ExerciseEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class,
        SetEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun setDao(): SetDao
}