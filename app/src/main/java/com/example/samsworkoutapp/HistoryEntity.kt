package com.example.samsworkoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class represents the table entity i.e. the schema of the database.
 *
 * @author Samarth Bedre
 */
@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    var date: String
)
