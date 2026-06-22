package com.example.strengthtraningprogression.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val muscleGroup: String,
    val equipment: String,
    val description: String?
)