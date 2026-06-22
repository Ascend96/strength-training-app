package com.example.strengthtraningprogression.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SetEntity(
    @PrimaryKey val id: String,
    val workoutExerciseId: String,
    val setNumber: Int,
    val reps: Int,
    val weight: Double,
    val notes: String?
)