package com.example.samsworkoutapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * This DAO interface allows the database to be accessed.
 *
 * @author Samarth Bedre
 */
@Dao
interface HistoryDao {

    /**
     * This method allows the user to insert the data into the database.
     */
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    /**
     * This method allows the user to query the database.
     */
    @Query("SELECT * FROM `history-table`")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}