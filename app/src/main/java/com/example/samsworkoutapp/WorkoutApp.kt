package com.example.samsworkoutapp

import android.app.Application

/**
 * This class represents the Application class of the app.
 *
 * @author Samarth Bedre
 */
class WorkoutApp : Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}