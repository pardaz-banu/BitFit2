package com.example.bitfit1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private lateinit var exerciseItemDao: ExerciseItemDao
    private lateinit var averageCaloriesBar: ProgressBar
    private lateinit var maxCaloriesBar: ProgressBar
    private lateinit var averageCaloriesText: TextView
    private lateinit var maxCaloriesText: TextView
    private lateinit var addRecordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        exerciseItemDao = ExerciseApplication.exerciseItemDatabase.exerciseItemDao()
        averageCaloriesBar = view.findViewById(R.id.average_calories_bar)
        maxCaloriesBar = view.findViewById(R.id.max_calories_bar)
        averageCaloriesText = view.findViewById(R.id.average_calories_text)
        maxCaloriesText = view.findViewById(R.id.max_calories_text)
        addRecordButton = view.findViewById(R.id.new_record_button)
        addRecordButton.setOnClickListener {
            navigateToAddRecord()
        }
        loadStatisticsFromDatabase()
        return view
    }

    private fun loadStatisticsFromDatabase() {
        lifecycleScope.launch(Dispatchers.Main) {
            exerciseItemDao.getAllExerciseItems().collectLatest { exerciseItems ->
                val averageCalories = calculateAverageCalories(exerciseItems)
                val maxCalories = calculateMaxCalories(exerciseItems)
                updateUI(averageCalories, maxCalories)
            }
        }
    }

    private fun calculateAverageCalories(exerciseItems: List<ExerciseItem>): Int {
        if (exerciseItems.isEmpty()) return 0
        var totalCalories = 0
        for (item in exerciseItems) {
            totalCalories += item.calories
        }
        return totalCalories / exerciseItems.size
    }

    private fun calculateMaxCalories(exerciseItems: List<ExerciseItem>): Int {
        if (exerciseItems.isEmpty()) return 0
        var maxCalories = Int.MIN_VALUE
        for (item in exerciseItems) {
            if (item.calories > maxCalories) {
                maxCalories = item.calories
            }
        }
        return maxCalories
    }

    private fun updateUI(averageCalories: Int, maxCalories: Int) {
        averageCaloriesBar.progress = averageCalories
        maxCaloriesBar.progress = maxCalories
        averageCaloriesText.text = "Average Calories Burnt: $averageCalories"
        maxCaloriesText.text = "Max Calories Burnt: $maxCalories"
    }

    private fun navigateToAddRecord() {
        val intent = Intent(activity, ExerciseItemActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}
