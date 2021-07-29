package com.example.foodapplication.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplication.R
import com.example.foodapplication.exercises.LoadingDialog
import com.example.foodapplication.exercises.NutritionixAPI
import com.example.foodapplication.progressDatabase.CaloriesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt

class ExerciseFragment : Fragment() {

    private var calories = 0.0
    private lateinit var caloriesViewModel: CaloriesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_exercise, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val userEmail = checkNotNull(arguments?.getString("userEmail"))
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY,0)
        calender.set(Calendar.MINUTE,0)
        calender.set(Calendar.SECOND,0)
        calender.set(Calendar.MILLISECOND,0)
        val currentDate = calender.timeInMillis
        val setOfCalories = mutableSetOf<Double>()
        caloriesViewModel = ViewModelProvider(this).get(CaloriesViewModel::class.java)
        caloriesViewModel.readUserEntryDate(userEmail,currentDate.toFloat()).observe(viewLifecycleOwner,{
                caloriesForCurrentDate ->
            for(items in caloriesForCurrentDate){
                if (caloriesForCurrentDate.size > 1) {
                    var updatedCals = 0.0
                    for (items in caloriesForCurrentDate) {
                        updatedCals += items.calories
                    }
                    setOfCalories.add(updatedCals)
                    getCaloriesAndExercise(setOfCalories)
                }
                else{
                    val updatedCals = items.calories
                    setOfCalories.add(updatedCals.toDouble())
                    getCaloriesAndExercise(setOfCalories)
                }
            }
        })
    }

    private fun getCaloriesAndExercise(setOfCalories: MutableSet<Double>) {

        calories = 0.0
        val view = requireView()
        for (i in setOfCalories){
            calories += i
        }
        val nutritionix = NutritionixAPI(calories)
        var btnGetExerciseAmmount = view.findViewById<Button>(R.id.getExerciseAmmountBtn)
        var etExercise = view.findViewById<EditText>(R.id.exerciseET)

        btnGetExerciseAmmount?.setOnClickListener {
            var exercise = etExercise?.text.toString()
            nutritionix.makeApiCall(exercise)
            GlobalScope.launch {
                printOutput(nutritionix)
            }
            val loading = LoadingDialog(requireActivity())
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable{
                override fun run() {
                    loading.isDismiss()
                }
            },5000)
        }
    }

    private suspend fun printOutput(nutritionix: NutritionixAPI){
        delay(5000L)
        this.activity?.runOnUiThread{
            var tvTotalCals = requireView().findViewById<TextView>(R.id.totalCaloriesTV)
            var tvOutputExerciseAmount = requireView().findViewById<TextView>(R.id.outputExerciseAmmountTV)
            var tvExerciseType = requireView().findViewById<TextView>(R.id.exerciseTypeTV)

            tvTotalCals.text = ("Workout ${calories.roundToInt()} cals")
            tvExerciseType.text = ("Exercise type: "+nutritionix.get_exercise_type())
            tvOutputExerciseAmount.text = ("Required: "+nutritionix.exerciseAmount+" min")

        }
    }

}