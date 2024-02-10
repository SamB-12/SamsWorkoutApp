package com.example.samsworkoutapp

/**
 * This data class represents the schema of how each exercise is stored in the app.
 *
 * @author Samarth Bedre
 */
data class ExerciseModel(
    var id: Int,
    var name: String,
    var image: Int,
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false
)
