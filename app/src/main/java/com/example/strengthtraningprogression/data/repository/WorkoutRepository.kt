package com.example.strengthtraningprogression.data.repository

import com.example.strengthtraningprogression.data.local.dao.ExerciseDao
import com.example.strengthtraningprogression.data.local.dao.SetDao
import com.example.strengthtraningprogression.data.local.dao.WorkoutDao
import com.example.strengthtraningprogression.data.local.dao.WorkoutExerciseDao
import com.example.strengthtraningprogression.data.local.entity.ExerciseEntity
import com.example.strengthtraningprogression.data.local.entity.SetEntity
import com.example.strengthtraningprogression.data.local.entity.WorkoutEntity
import com.example.strengthtraningprogression.data.local.entity.WorkoutExerciseEntity
import java.util.UUID

class WorkoutRepository(
    private val workoutDao: WorkoutDao,
    private val workoutExerciseDao: WorkoutExerciseDao,
    private val setDao: SetDao,
    private val exerciseDao: ExerciseDao
) {

    suspend fun createWorkout(): String {
        val id = UUID.randomUUID().toString()

        workoutDao.insert(
            WorkoutEntity(
                id = id,
                date = System.currentTimeMillis(),
                duration = 0,
                notes = null
            )
        )

        return id
    }

    suspend fun addExerciseToWorkout(workoutId: String, exerciseId: String): String {
        val id = UUID.randomUUID().toString()

        workoutExerciseDao.insert(
            WorkoutExerciseEntity(
                id = id,
                workoutId = workoutId,
                exerciseId = exerciseId,
                sortOrder = 0
            )
        )

        return id
    }

    suspend fun addSet(workoutExerciseId: String, reps: Int, weight: Double) {
        setDao.insert(
            SetEntity(
                id = UUID.randomUUID().toString(),
                workoutExerciseId = workoutExerciseId,
                setNumber = 0,
                reps = reps,
                weight = weight,
                notes = null
            )
        )
    }

    suspend fun addExercise(exercise: ExerciseEntity) {
        exerciseDao.insert(exercise)
    }

    suspend fun getWorkoutExercises(workoutId: String): List<WorkoutExerciseEntity> {
        return workoutExerciseDao.getByWorkoutId(workoutId)
    }

    suspend fun getSetsForWorkoutExercise(id: String): List<SetEntity> {
        return setDao.getByWorkoutExerciseId(id)
    }

    suspend fun getExerciseById(id: String): ExerciseEntity {
        return exerciseDao.getById(id)
    }

    suspend fun getLatestWorkout(): WorkoutEntity? {
        return workoutDao.getLatestWorkout()
    }

    suspend fun getAllWorkouts(): List<WorkoutEntity> {
        return workoutDao.getAllWorkouts()
    }

    suspend fun getWorkoutsInRange(start: Long): List<WorkoutEntity> {
        return workoutDao.getAllWorkouts()
            .filter { it.date >= start }
    }

    suspend fun getSets(workoutExerciseId: String): List<SetEntity> {
        return setDao.getByWorkoutExerciseId(workoutExerciseId)
    }

    suspend fun deleteSet(set: SetEntity) {
        setDao.delete(set)
    }

    suspend fun deleteExerciseFromWorkout(workoutExerciseId: String) {
        // sets must be deleted before removing the join row
        setDao.deleteByWorkoutExerciseId(workoutExerciseId)
        workoutExerciseDao.deleteById(workoutExerciseId)
    }

    suspend fun getSetsForExercise(exerciseId: String): List<SetEntity> {
        return setDao.getSetsByExerciseId(exerciseId)
    }
}