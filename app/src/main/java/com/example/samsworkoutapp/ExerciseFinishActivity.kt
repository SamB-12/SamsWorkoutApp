package com.example.samsworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.samsworkoutapp.databinding.ActivityExerciseFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * This class represents the activity that comes up when the exercies are finished..
 *
 * @author Samarth Bedre
 */
class ExerciseFinishActivity : AppCompatActivity() {

    private var binding: ActivityExerciseFinishBinding? = null

    /**
     * This method initialises the screen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarFinish)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolBarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        addDateToDatabase(historyDao)

        setupFinishBtn()


    }

    /**
     * This method takes the user back to them main screen when the exercises are finished.
     */
    private fun setupFinishBtn() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        binding?.btnFinish?.setOnClickListener {
            startActivity(intent)
        }
    }

    /**
     * This method adds the finished exercise to the database.
     */
    private fun addDateToDatabase(historyDao: HistoryDao) {
        val calendar: Calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val sdf: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date: String = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }
    }

    /**
     * This method destroys all the objects when the activity is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()

        if (binding != null) {
            binding = null
        }
    }
}