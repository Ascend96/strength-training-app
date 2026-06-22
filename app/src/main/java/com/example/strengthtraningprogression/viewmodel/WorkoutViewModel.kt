package com.example.strengthtraningprogression.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.strengthtraningprogression.data.local.entity.ExerciseEntity
import com.example.strengthtraningprogression.data.local.entity.SetEntity
import com.example.strengthtraningprogression.data.local.entity.WorkoutEntity
import com.example.strengthtraningprogression.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.util.UUID

data class WorkoutUiModel(
    val exercises: List<ExerciseUiModel> = emptyList()
)

data class ExerciseUiModel(
    val id: String,
    val name: String,
    val sets: List<SetUiModel> = emptyList()
)

data class SetUiModel(
    val reps: Int,
    val weight: Double
)

data class ProgressSummary(
    val workoutCount: Int,
    val exerciseCount: Int,
    val totalVolume: Double
)

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _workout = MutableStateFlow(WorkoutUiModel())
    val workout = _workout.asStateFlow()

    private val _workouts = MutableStateFlow<List<WorkoutEntity>>(emptyList())
    val workouts = _workouts.asStateFlow()

    private val _summary = MutableStateFlow(ProgressSummary(0, 0, 0.0))
    val summary = _summary.asStateFlow()

    private var currentWorkoutId: String? = null

    init {
        startWorkout()
    }

    fun startWorkout() {
        viewModelScope.launch {
            currentWorkoutId = repository.createWorkout()
            loadWorkout()
        }
    }

    fun loadWorkouts() {
        viewModelScope.launch {
            _workouts.value = repository.getAllWorkouts()
        }
    }

    fun addExercise(name: String) {
        viewModelScope.launch {
            val exerciseId = UUID.randomUUID().toString()

            val workoutId = currentWorkoutId ?: repository.createWorkout().also {
                currentWorkoutId = it
            }

            repository.addExercise(
                ExerciseEntity(
                    id = exerciseId,
                    name = name,
                    category = "General",
                    muscleGroup = "Unknown",
                    equipment = "None",
                    description = null
                )
            )

            val workoutExerciseId = repository.addExerciseToWorkout(workoutId, exerciseId)

            _workout.value = _workout.value.copy(
                exercises = _workout.value.exercises + ExerciseUiModel(
                    id = workoutExerciseId,
                    name = name
                )
            )
        }
    }

    fun addSet(exerciseId: String, reps: Int, weight: Double) {
        viewModelScope.launch {
            repository.addSet(
                workoutExerciseId = exerciseId,
                reps = reps,
                weight = weight
            )

            val updatedExercises = _workout.value.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    exercise.copy(sets = exercise.sets + SetUiModel(reps, weight))
                } else exercise
            }

            _workout.value = _workout.value.copy(exercises = updatedExercises)
        }
    }

    fun loadWorkout() {
        viewModelScope.launch {
            val workoutId = currentWorkoutId ?: return@launch

            val workoutExercises = repository.getWorkoutExercises(workoutId)

            val exercises = workoutExercises.map { we ->
                val exerciseEntity = repository.getExerciseById(we.exerciseId)
                val sets = repository.getSetsForWorkoutExercise(we.id)

                ExerciseUiModel(
                    id = we.id, // id is the join row id, not the exercise id
                    name = exerciseEntity.name,
                    sets = sets.map { SetUiModel(it.reps, it.weight) }
                )
            }

            _workout.value = WorkoutUiModel(exercises)
        }
    }

    fun finishWorkout() {
        currentWorkoutId = null
    }

    fun loadSummary(range: String) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()

            val start = when (range) {
                "week" -> now - (7L * 24 * 60 * 60 * 1000)
                "month" -> now - (30L * 24 * 60 * 60 * 1000)
                "year" -> now - (365L * 24 * 60 * 60 * 1000)
                else -> now
            }

            val workouts = repository.getWorkoutsInRange(start)

            var totalExercises = 0
            var totalVolume = 0.0

            workouts.forEach { workout ->
                val workoutExercises = repository.getWorkoutExercises(workout.id)
                totalExercises += workoutExercises.size

                workoutExercises.forEach { we ->
                    repository.getSets(we.id).forEach { set ->
                        totalVolume += set.reps * set.weight
                    }
                }
            }

            _summary.value = ProgressSummary(
                workoutCount = workouts.size,
                exerciseCount = totalExercises,
                totalVolume = totalVolume
            )
        }
    }

    fun deleteSet(exerciseId: String, setIndex: Int) {
        viewModelScope.launch {
            val exercise = _workout.value.exercises.find { it.id == exerciseId } ?: return@launch
            exercise.sets.getOrNull(setIndex) ?: return@launch

            val dbSet = repository.getSetsForWorkoutExercise(exerciseId)
                .getOrNull(setIndex) ?: return@launch

            repository.deleteSet(dbSet)

            val updatedExercises = _workout.value.exercises.map {
                if (it.id == exerciseId) {
                    it.copy(sets = it.sets.toMutableList().apply { removeAt(setIndex) })
                } else it
            }

            _workout.value = _workout.value.copy(exercises = updatedExercises)
        }
    }

    fun deleteExercise(workoutExerciseId: String) {
        viewModelScope.launch {
            repository.deleteExerciseFromWorkout(workoutExerciseId)

            _workout.value = _workout.value.copy(
                exercises = _workout.value.exercises.filter { it.id != workoutExerciseId }
            )
        }
    }

}