package com.example.bitfit1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest

class ExerciseAdapter(private var exerciseList: List<ExerciseItem>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        val durationTextView: TextView = itemView.findViewById(R.id.durationTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exercises, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem = exerciseList[position]
        holder.typeTextView.text = currentItem.type
        holder.durationTextView.text = "Duration: ${currentItem.duration} mins"
        holder.caloriesTextView.text = "Calories: ${currentItem.calories}"
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    fun setData(exerciseItems: List<ExerciseItem>) {
        exerciseList = exerciseItems
        notifyDataSetChanged()
    }
}
