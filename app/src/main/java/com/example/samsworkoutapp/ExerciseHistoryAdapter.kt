package com.example.samsworkoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.samsworkoutapp.databinding.ItemHistoryBinding

/**
 * This method is an Adapter for the Recycler View that displays the history of the exercises completed.
 *
 * @author Samarth Bedre
 */
class ExerciseHistoryAdapter(val exerciseList: ArrayList<String>) :
    RecyclerView.Adapter<ExerciseHistoryAdapter.HistoryViewHolder>() {

    /**
     * This class is a view holder for the all the items in the Recycler View.
     *
     * @author Samarth Bedre
     */
    class HistoryViewHolder(binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val llHistoryCard = binding.llHistoryCard
        val tvHistoryId = binding.tvHistoryId
        val tvHistoryDate = binding.tvHistoryDate
    }

    /**
     * This method initialises the view when the view holder is created.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * This method gets the count of number of items in the list.
     */
    override fun getItemCount(): Int {
        return exerciseList.size
    }

    /**
     * This method binds the data to the view holder.
     */
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val date: String = exerciseList.get(position)
        holder.tvHistoryId.text = (position + 1).toString()
        holder.tvHistoryDate.text = date
    }
}