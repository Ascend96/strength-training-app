package com.example.strengthtraningprogression.data.local.dao

import androidx.room.*
import com.example.strengthtraningprogression.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM ExerciseEntity")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Insert
    suspend fun insert(exercise: ExerciseEntity)

    @Delete
    suspend fun delete(exercise: ExerciseEntity)

    @Query("SELECT * FROM ExerciseEntity WHERE id = :id")
    suspend fun getById(id: String): ExerciseEntity
}