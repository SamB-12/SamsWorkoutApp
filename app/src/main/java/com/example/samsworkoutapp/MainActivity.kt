package com.example.samsworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.samsworkoutapp.databinding.ActivityMainBinding

/**
 * This class represents the main activity of the app.
 *
 * @author Samarth Bedre
 */
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    /**
     * This method initialises the main screen of the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {
            val intent: Intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMIButton?.setOnClickListener {
            val intent: Intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistoryButton?.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * This method destroys all objects when activity is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}