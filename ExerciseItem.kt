package com.example.bitfit1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_items")
data class ExerciseItem(
    val type: String,
    val duration: Int,
    val calories: Int,
    @PrimaryKey(autoGenerate = true) var id: Long = 0
)