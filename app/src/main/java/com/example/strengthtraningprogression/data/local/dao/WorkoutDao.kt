package com.example.strengthtraningprogression.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.strengthtraningprogression.data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insert(workout: WorkoutEntity)

    @Query("SELECT * FROM WorkoutEntity ORDER BY date DESC LIMIT 1")
    suspend fun getLatestWorkout(): WorkoutEntity?

    @Query("SELECT * FROM WorkoutEntity ORDER BY date DESC")
    suspend fun getAllWorkouts(): List<WorkoutEntity>
}