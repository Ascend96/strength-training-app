package com.example.strengthtraningprogression.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutEntity(
    @PrimaryKey val id: String,
    val date: Long,
    val duration: Int,
    val notes: String?
)
