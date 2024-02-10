package com.example.samsworkoutapp

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.LANG_MISSING_DATA
import android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED
import android.speech.tts.TextToSpeech.QUEUE_ADD
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samsworkoutapp.databinding.ActivityExcerciseBinding
import com.example.samsworkoutapp.databinding.CustomBackDialogConfirmationBinding
import java.util.Locale

/**
 * This class shows the exercises along with the timer.
 *
 * It implements TextToSpeech and MediaPlayer for playing sounds whenever the exercises start and end.
 *
 * @author Samarth Bedre
 */
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExcerciseBinding? = null

    private var timer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var remainingProgress = 0
    private val maxBreakTime = 10
    private val maxExerciseTime = 30

    private val exerciseList: ArrayList<ExerciseModel> = Constants.defaultExerciseList()
    private var currentExercise = 0

    private var tts: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null

    private var exerciseStatusAdapter:ExerciseStatusAdapter ?= null

    /**
     * This method sets up the screen when the activity is started.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarExercise)

        tts = TextToSpeech(this, this)
        val soundURI =
            Uri.parse("android.resource://com.example.samsworkoutapp/" + R.raw.press_start)
        mediaPlayer = MediaPlayer.create(this, soundURI)
        mediaPlayer?.isLooping = false

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolBarExercise?.setNavigationOnClickListener {
            customBackButtonDialog()
        }

        setupExerciseStatusRV()

        setBreakTimer()

    }

    private fun customBackButtonDialog(){
        val customDialog = Dialog(this)
        val dialogBinding:CustomBackDialogConfirmationBinding = CustomBackDialogConfirmationBinding.inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()

        customDialog.window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
    }

    private fun setupExerciseStatusRV(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList)
        binding?.rvExerciseStatus?.adapter = exerciseStatusAdapter
    }

    /**
     * This method sets up rest screen and the timer(rest) whenever the exercise is about to begin.
     */
    private fun setBreakTimer() {
        mediaPlayer?.start()

        binding?.ivExercise?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "GET READY FOR "
        binding?.tvNextExerciseLabel?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvNextExercise?.text = exerciseList[currentExercise].name
        binding?.progressBar?.progress = 10 - remainingProgress

        timer = object : CountDownTimer((maxBreakTime*1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingProgress += 1
                binding?.progressBar?.progress = maxBreakTime - remainingProgress
                binding?.tvTimer?.text = (maxBreakTime - remainingProgress).toString()
            }

            override fun onFinish() {
                //Toast.makeText(this@ExerciseActivity,"Lez Go! Start the Exercise",Toast.LENGTH_LONG).show()
                remainingProgress = 0
                timer?.cancel()
                binding?.ivExercise?.visibility = View.VISIBLE
                setExerciseTimer()
            }

        }.start()
    }

    /**
     * This method sets up the exercises and the associated timer for the duration of the exercise.
     */
    private fun setExerciseTimer() {
        binding?.tvNextExerciseLabel?.visibility = View.INVISIBLE
        binding?.tvNextExercise?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = exerciseList[currentExercise].name
        binding?.ivExercise?.setImageResource(exerciseList[currentExercise].image)
        binding?.progressBar?.max = maxExerciseTime
        binding?.progressBar?.progress = maxExerciseTime - remainingProgress

        exerciseList[currentExercise].isSelected = true
        exerciseStatusAdapter?.notifyItemChanged(currentExercise)

        speak(exerciseList[currentExercise].name)

        exerciseTimer = object : CountDownTimer((maxExerciseTime*1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingProgress++
                binding?.progressBar?.progress = maxExerciseTime - remainingProgress
                binding?.tvTimer?.text = (maxExerciseTime - remainingProgress).toString()
            }

            override fun onFinish() {
                exerciseList[currentExercise].isSelected = false
                exerciseList[currentExercise].isCompleted = true
                exerciseStatusAdapter?.notifyItemChanged(currentExercise)
                remainingProgress = 0
                currentExercise++
                if (currentExercise < exerciseList.size) {
                    setBreakTimer()
                } else {
                    exerciseTimer?.cancel()
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Finished the 7 Minute Exercises",
                        Toast.LENGTH_LONG
                    ).show()
                    speak("Well Done!, You finished all the exercises")
                    finish()
                    val intent:Intent =Intent(this@ExerciseActivity,ExerciseFinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }

    /**
     * This method initialises the TextToSpeech object and sets up the language.
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val response =
                tts?.setLanguage(Locale.UK) //sets up the language and returns the status message

            if (response == LANG_NOT_SUPPORTED || response == LANG_MISSING_DATA) {
                Log.e("Error", "Language not on the Phone")
            }
        } else {
            Log.e("error msg", "Not initialised")
        }
    }

    /**
     * This method converts the text to speech.
     */
    private fun speak(text: String) {
        tts?.speak(text, QUEUE_ADD, null, "")
    }

    override fun onBackPressed() {
        customBackButtonDialog()
        //super.onBackPressed()
    }

    /**
     * This method is used to close and destroy all objects that were generated during the execution of this activity.
     */
    override fun onDestroy() {
        super.onDestroy()

        if (timer != null) {
            timer?.cancel()
            remainingProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            remainingProgress = 0
        }

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        if (mediaPlayer != null) {
            mediaPlayer?.stop()
        }

        binding = null
    }
}