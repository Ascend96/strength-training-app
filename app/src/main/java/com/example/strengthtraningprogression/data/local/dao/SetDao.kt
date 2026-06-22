package com.example.strengthtraningprogression.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.strengthtraningprogression.data.local.entity.SetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {
    @Insert
    suspend fun insert(set: SetEntity)

    @Query("SELECT * FROM SetEntity WHERE workoutExerciseId = :id")
    suspend fun getByWorkoutExerciseId(id: String): List<SetEntity>

    @Delete
    suspend fun delete(set: SetEntity)

    @Query("DELETE FROM SetEntity WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun deleteByWorkoutExerciseId(workoutExerciseId: String)

    @Query("""
    SELECT s.* FROM SetEntity s
    INNER JOIN WorkoutExerciseEntity we 
    ON s.workoutExerciseId = we.id
    WHERE we.exerciseId = :exerciseId
""")
    suspend fun getSetsByExerciseId(exerciseId: String): List<SetEntity>
}