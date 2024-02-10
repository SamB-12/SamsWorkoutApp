package com.example.samsworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samsworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * This class takes care of displaying the history of all the exercises completed by the user.
 *
 * @author Samarth Bedre
 */

class HistoryActivity : AppCompatActivity() {

    var binding: ActivityHistoryBinding? = null

    /**
     * This method initialises the screen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarHistory)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }

        binding?.toolBarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(historyDao)
    }

    /**
     * This method gets all the exercises completed details.
     */
    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { completedDates ->
                if (completedDates.isNotEmpty()) {
                    binding?.tvCompletedMessage?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoExerciseMessage?.visibility = View.GONE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates: ArrayList<String> = ArrayList<String>()

                    for (date in completedDates) {
                        dates.add(date.date)
                    }

                    val historyAdapter: ExerciseHistoryAdapter = ExerciseHistoryAdapter(dates)

                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    binding?.tvCompletedMessage?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoExerciseMessage?.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * This method destroys the created objects when the activity is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()

        if (binding != null) {
            binding = null
        }
    }
}