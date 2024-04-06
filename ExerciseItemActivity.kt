package com.example.bitfit1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseItemActivity : AppCompatActivity() {

    private lateinit var exerciseItemDao: ExerciseItemDao

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_item)

        val spinner: Spinner = findViewById(R.id.exercise_type)

        // Define the placeholder and items for the Spinner
        val placeholder = "Select Exercise Type"

        // Define the items for the Spinner
        val exerciseTypes = listOf(placeholder,"Full Body", "Lower Body", "Upper Body", "Running")

        // Create an ArrayAdapter using the defined items and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, exerciseTypes)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinner.adapter = adapter

        // Set the initial selection to the placeholder
        spinner.setSelection(0)

        // Find the TextView for "Go back"
        val goBackTextView: TextView = findViewById(R.id.goBackTextView)

        // Set click listener for the TextView
        goBackTextView.setOnClickListener {
            // Finish the current activity to go back
            finish()
        }

        exerciseItemDao = ExerciseApplication.exerciseItemDatabase.exerciseItemDao()

        val submitBtn: Button = findViewById(R.id.submitBtn)
        val typeEditText: Spinner = findViewById(R.id.exercise_type)
        val durationEditText: EditText = findViewById(R.id.exercise_duration)
        val caloriesEditText: EditText = findViewById(R.id.calories_burnt)

        submitBtn.setOnClickListener {
            val type = typeEditText.selectedItem.toString()
            val duration = durationEditText.text.toString().toIntOrNull() ?: 0
            val calories = caloriesEditText.text.toString().toIntOrNull() ?: 0

            val exerciseItem = ExerciseItem(type, duration, calories)
            Log.i("Mahima exerciseItem",exerciseItem.toString())
            lifecycleScope.launch(Dispatchers.IO) {
               exerciseItemDao.insert(exerciseItem)
            }
            val intent = Intent(this@ExerciseItemActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}