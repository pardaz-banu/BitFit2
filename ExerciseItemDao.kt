package com.example.bitfit1
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseItemDao {
    @Insert
    fun insert(exerciseItem: ExerciseItem)

    @Query("SELECT * FROM exercise_items")
    fun getAllExerciseItems(): Flow<List<ExerciseItem>>

    @Query("DELETE FROM exercise_items")
    fun clearTable(): Void
}