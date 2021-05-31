package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class InputFragment : Fragment(R.layout.fragment_input){

    var calories = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_input, container, false)

        var mockFoodData: HashMap<String, Int> = hashMapOf("apple" to 95, "banana" to 89, "egg" to 155)
        var editTextFood: EditText = root.findViewById(R.id.txtFood)
        var amount: EditText = root.findViewById(R.id.txtAmount)
        val btnAdd :Button = root.findViewById(R.id.btnAdd)
        val textViewCalories: TextView = root.findViewById(R.id.textView2)
        val btnClear:Button = root.findViewById(R.id.btnClear)
        var textViewOutput: TextView = root.findViewById(R.id.tViewFoods)
        val food = editTextFood.text
        val foodAmount = amount.text


        btnAdd.setOnClickListener {
            when {
                food.toString() == "Apple" -> {
                    calories += if(foodAmount.toString()  == ""){
                        mockFoodData["apple"]!!
                    } else{
                        mockFoodData["apple"]?.times(foodAmount.toString().toInt())!!
                    }
                }
                food.toString() == "Banana" -> {
                    calories += if(foodAmount.toString()  == ""){
                        mockFoodData["banana"]!!
                    } else{
                        mockFoodData["banana"]?.times(foodAmount.toString().toInt())!!
                    }
                }
                food.toString() == "Egg" -> {
                    calories += if(foodAmount.toString()  == ""){
                        mockFoodData["egg"]!!
                    } else{
                        mockFoodData["egg"]?.times(foodAmount.toString().toInt())!!
                    }
                }
            }

            var textViewCalories : TextView = root.findViewById(R.id.textView2)

            textViewCalories.text = calories.toString()
            textViewOutput.append("$food $foodAmount\n")
        }

        btnClear.setOnClickListener{
            calories = 0
            textViewCalories.text = calories.toString()
            textViewOutput.text = ""
        }

        return root
    }
}