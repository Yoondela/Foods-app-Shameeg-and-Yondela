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
import com.example.foodapplication.R
import com.example.foodapplication.exercises.LoadingDialog
import com.example.foodapplication.exercises.NutritionixAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseFragment : Fragment() {

    private var calories = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)

        var btnGetExerciseAmmount = view.findViewById<Button>(R.id.getExerciseAmmountBtn)
        var etExercise = view.findViewById<EditText>(R.id.exerciseET)

        calories = checkNotNull(arguments?.getDouble("Calories"))

        val nutritionix = NutritionixAPI(calories)

        btnGetExerciseAmmount.setOnClickListener {
            var exercise = etExercise.text.toString()
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

        return view
    }

    private suspend fun printOutput(nutritionix: NutritionixAPI){
        delay(5000L)
        this.activity?.runOnUiThread{
            var tvTotalCals = requireView().findViewById<TextView>(R.id.totalCaloriesTV)
            var tvOutputExerciseAmount = requireView().findViewById<TextView>(R.id.outputExerciseAmmountTV)
            var tvExerciseType = requireView().findViewById<TextView>(R.id.exerciseTypeTV)

            tvTotalCals.text = ("Workout "+calories+" cals")
            tvExerciseType.text = ("Exercise type: "+nutritionix.get_exercise_type())
            tvOutputExerciseAmount.text = ("Required: "+nutritionix.exerciseAmount+" min")

        }
    }

}