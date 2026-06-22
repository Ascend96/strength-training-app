package com.example.strengthtraningprogression.data

import java.util.UUID

data class Workout(
    val id: UUID = UUID.randomUUID(),
    val exercises: MutableList<WorkoutExercise> = mutableListOf()
)

data class WorkoutExercise(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val sets: MutableList<SetEntry> = mutableListOf()
)

data class SetEntry(
    val id: UUID = UUID.randomUUID(),
    val reps: Int,
    val weight: Double
)