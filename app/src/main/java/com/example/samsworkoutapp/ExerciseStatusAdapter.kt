package com.example.samsworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.samsworkoutapp.databinding.ItemExerciseStatusBinding

/**
 * This class acts as an Adapter for the Exercise number Recycler View.
 *
 * @author Samarth Bedre
 */
class ExerciseStatusAdapter(val exerciseList:ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    /**
     * This class acts as the view holder for the Recycler View.
     *
     * @author Samarth Bedre
     */
    inner class ViewHolder(binding: ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvItem
    }

    /**
     * This method initialises the view holder to a view when created.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    /**
     * This method gets to count of the exercises in the list.
     */
    override fun getItemCount(): Int {
       return exerciseList.size
    }

    /**
     * This method binds the data to the view holder.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise:ExerciseModel = exerciseList[position]
        holder.tvItem.text = exercise.id.toString()

        when{
            exercise.isSelected -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_thin_color_accent_border)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }

            exercise.isCompleted -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }

            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_gray_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }
}