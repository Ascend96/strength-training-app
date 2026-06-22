package com.example.strengthtraningprogression.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.strengthtraningprogression.data.local.entity.WorkoutExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {

    @Insert
    suspend fun insert(workoutExercise: WorkoutExerciseEntity)

    @Query("SELECT * FROM WorkoutExerciseEntity WHERE workoutId = :workoutId")
    suspend fun getByWorkoutId(workoutId: String): List<WorkoutExerciseEntity>

    @Query("DELETE FROM WorkoutExerciseEntity WHERE id = :id")
    suspend fun deleteById(id: String)
}