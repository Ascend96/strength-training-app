package com.example.strengthtraningprogression.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthtraningprogression.data.local.entity.ExerciseEntity
import com.example.strengthtraningprogression.data.local.entity.SetEntity
import com.example.strengthtraningprogression.data.repository.ExerciseRepository
import com.example.strengthtraningprogression.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _exerciseSets = MutableStateFlow<List<SetEntity>>(emptyList())
    val exerciseSets = _exerciseSets.asStateFlow()

    val exercises = repository.getExercises()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addExercise(name: String) {
        viewModelScope.launch {
            repository.addExercise(
                ExerciseEntity(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    category = "General",
                    muscleGroup = "Unknown",
                    equipment = "None",
                    description = null
                )
            )
        }
    }

    fun loadSetsForExercise(exerciseId: String) {
        viewModelScope.launch {
            _exerciseSets.value = workoutRepository.getSetsForExercise(exerciseId)
        }
    }
}