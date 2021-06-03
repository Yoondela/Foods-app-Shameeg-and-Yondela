package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class MainActivity : AppCompatActivity(), Communicator {

    var calories = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState==null){
            setDefaultScreen()
        }
    }

    private fun setDefaultScreen() {

        val inputFragment = InputFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, inputFragment)
            commit()
        }
    }

    override fun passData(calories: String) {
        val bundle = Bundle()
        bundle.putString("calories", calories)

        val transaction = this.supportFragmentManager.beginTransaction()
        val outputFragment = OutputFragment()

        outputFragment.arguments = bundle
        transaction.replace(R.id.mainLayout, outputFragment)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun checkCals():Int{


        val mockFoodData: HashMap<String, Int> =
            hashMapOf("apple" to 95, "banana" to 89, "egg" to 155)
        val editTextFood: EditText = findViewById(R.id.txtFood)
        val amount: EditText = findViewById(R.id.txtAmount)
        val food = editTextFood.text
        val foodAmount = amount.text

        when {
            food.toString() == "Apple" -> {
                calories += if (foodAmount.toString() == "") {
                    mockFoodData["apple"]!!
                } else {
                    mockFoodData["apple"]?.times(foodAmount.toString().toInt())!!
                }
            }
            food.toString() == "Banana" -> {
                calories += if (foodAmount.toString() == "") {
                    mockFoodData["banana"]!!
                } else {
                    mockFoodData["banana"]?.times(foodAmount.toString().toInt())!!
                }
            }
            food.toString() == "Egg" -> {
                calories += if (foodAmount.toString() == "") {
                    mockFoodData["egg"]!!
                } else {
                    mockFoodData["egg"]?.times(foodAmount.toString().toInt())!!
                }
            }
        }
        return calories
    }

    override fun resetCals():Int{
        calories = 0

        return calories
    }
}