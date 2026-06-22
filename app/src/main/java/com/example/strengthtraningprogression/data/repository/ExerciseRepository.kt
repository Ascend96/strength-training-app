package com.example.strengthtraningprogression.data.repository

import com.example.strengthtraningprogression.data.local.dao.ExerciseDao
import com.example.strengthtraningprogression.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val dao: ExerciseDao
) {

    fun getExercises(): Flow<List<ExerciseEntity>> {
        return dao.getAll()
    }

    suspend fun addExercise(exercise: ExerciseEntity) {
        dao.insert(exercise)
    }
}