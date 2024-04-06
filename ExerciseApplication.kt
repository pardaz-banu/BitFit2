package com.example.bitfit1

import android.app.Application
import androidx.room.Room

class ExerciseApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }

    companion object {
        lateinit var exerciseItemDatabase: AppDatabase
    }


    override fun onCreate() {
        super.onCreate()

        exerciseItemDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "exercise_database"
        ).build()
    }
}