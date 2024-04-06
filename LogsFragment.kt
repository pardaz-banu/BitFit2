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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LogsFragment : Fragment() {

    private lateinit var exerciseItemDao: ExerciseItemDao
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logs, container, false)
        exerciseItemDao = ExerciseApplication.exerciseItemDatabase.exerciseItemDao()
        recyclerView = view.findViewById(R.id.rcview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        exerciseAdapter = ExerciseAdapter(emptyList())
        recyclerView.adapter = exerciseAdapter
        loadDataFromDatabase()

        // Initialize the button and set OnClickListener
        val addButton = view.findViewById<Button>(R.id.new_record_button)
        addButton.setOnClickListener {
            // Handle button click - navigate to ExerciseItemActivity
            val intent = Intent(requireContext(), ExerciseItemActivity::class.java)
            startActivity(intent)
        }

        return view
    }


    private fun loadDataFromDatabase() {
        lifecycleScope.launch(Dispatchers.Main) {
            exerciseItemDao.getAllExerciseItems().collectLatest { exerciseItems ->
                exerciseAdapter.setData(exerciseItems)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): LogsFragment {
            return LogsFragment()
        }
    }
}
