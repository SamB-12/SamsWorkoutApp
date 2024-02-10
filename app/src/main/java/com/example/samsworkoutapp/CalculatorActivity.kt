package com.example.samsworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.samsworkoutapp.databinding.ActivityCalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class CalculatorActivity : AppCompatActivity() {
    var binding:ActivityCalculatorBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarBMI)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }

        binding?.toolBarBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            if (!validateMetricUnits()){
                Toast.makeText(this,"Enter Valid Input",Toast.LENGTH_SHORT).show()
            } else{
                calculateBMI()
            }
        }

        binding?.rbImperial?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding?.inputLayout2?.visibility = View.INVISIBLE
                binding?.imperialLayout?.visibility = View.VISIBLE
                binding?.inputLayout1?.visibility = View.INVISIBLE
                binding?.inputLayout5?.visibility = View.VISIBLE
                clearInputFields()
            } else{
                binding?.imperialLayout?.visibility = View.GONE
                binding?.inputLayout2?.visibility = View.VISIBLE
                binding?.inputLayout5?.visibility = View.GONE
                binding?.inputLayout1?.visibility = View.VISIBLE
                clearInputFields()
            }
        }
    }

    private fun clearInputFields(){
        binding?.tvBMIHeading?.text = ""
        binding?.tvBMI?.text = ""
        binding?.tvBMIStatus?.text = ""
        binding?.tvBMIMessage?.text = ""
        binding?.inputWeight?.text!!.clear()
        binding?.inputHeight?.text!!.clear()
        binding?.inputImperialWeight?.text!!.clear()
        binding?.inputFeet?.text!!.clear()
        binding?.inputInch?.text!!.clear()
    }

    private fun validateMetricUnits():Boolean{
        var isValid:Boolean = true

        if (binding?.rbMetric!!.isChecked){
            when{
                binding?.inputHeight?.text.toString().isEmpty() -> {
                    isValid = false
                }
                binding?.inputWeight?.text.toString().isEmpty() -> {
                    isValid = false
                }
            }
        } else if (binding?.rbImperial!!.isChecked){
            when{
                binding?.inputFeet?.text.toString().isEmpty() -> {
                    isValid = false
                }
                binding?.inputImperialWeight?.text.toString().isEmpty() -> {
                    isValid = false
                }
                binding?.inputInch?.text.toString().isEmpty() -> {
                    isValid = false
                }
            }
        }

        return isValid
    }

    private fun calculateBMI(){
        var bmi:Float = 0f

        if (binding?.rbMetric!!.isChecked){
            val height:Float = (binding?.inputHeight?.text.toString()).toFloat() / 100
            val weight:Float = (binding?.inputWeight?.text.toString()).toFloat()

            bmi = weight / (height*height)
        } else if (binding?.rbImperial!!.isChecked){
            val weight:Float = (binding?.inputImperialWeight?.text.toString()).toFloat()

            val feet:Float = (binding?.inputFeet?.text.toString()).toFloat()
            val inch:Float = (binding?.inputInch?.text.toString()).toFloat()

            val height:Float = inch + feet * 12

            bmi = (weight / (height*height)) * 703
        }

        displayBMI(bmi)
    }

    private fun displayBMI(bmi:Float){
        val bmiStatus:String
        val bmiMessage:String

        val roundedBmi:Float = bmi.toBigDecimal().setScale(2,RoundingMode.FLOOR).toFloat()

        binding?.tvBMIHeading?.visibility = View.VISIBLE
        binding?.tvBMI?.text = roundedBmi.toString()
        binding?.tvBMI?.visibility = View.VISIBLE

        if(bmi.compareTo(15f) <= 0){
            bmiStatus = "Very severely Underweight"
            bmiMessage = "Opps! You really need to take better care of yourself! Eat More!"
        } else if (bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){
            bmiStatus = "Severely Underweight"
            bmiMessage = "Opps! You really need to take better care of yourself! Eat More!"
        } else if (bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiStatus = "Underweight"
            bmiMessage = "Opps! You really need to take better care of yourself! Eat More!"
        } else if (bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0){
            bmiStatus = "Normal"
            bmiMessage = "Congratulations!! You are Perfect!"
        } else if (bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiStatus = "Overweight"
            bmiMessage = "Oops! You really need to take care of yourself! Workout maybe?"
        } else if (bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0){
            bmiStatus = "Obese Class | (Moderately Obese)"
            bmiMessage = "Oops! You really need to take care of yourself! Workout maybe?"
        } else if (bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0){
            bmiStatus = "Obese Class || (Severely Obese)"
            bmiMessage = "OMG! You are in dangerous condition! Act Now!!"
        } else{
            bmiStatus = "Obese Class ||| (Very Severely Obese)"
            bmiMessage = "OMG! You are in dangerous condition! Act Now!!"
        }

        binding?.tvBMIStatus?.text = bmiStatus
        binding?.tvBMIStatus?.visibility = View.VISIBLE
        binding?.tvBMIMessage?.text = bmiMessage
        binding?.tvBMIMessage?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding!=null){
            binding = null
        }
    }
}