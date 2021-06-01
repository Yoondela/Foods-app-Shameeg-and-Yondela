package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import androidx.fragment.app.FragmentTransaction

class InputFragment : Fragment(R.layout.fragment_input){

    private var calories = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val root = inflater.inflate(R.layout.fragment_input, container, false)

        val mockFoodData: HashMap<String, Int> =
            hashMapOf("apple" to 95, "banana" to 89, "egg" to 155)
        val editTextFood: EditText = root.findViewById(R.id.txtFood)
        val amount: EditText = root.findViewById(R.id.txtAmount)
        val btnAdd: Button = root.findViewById(R.id.btnAdd)
        val textViewCalories: TextView = root.findViewById(R.id.textView2)
        val btnClear: Button = root.findViewById(R.id.btnClear)
        val food = editTextFood.text
        val foodAmount = amount.text
        val btnContinue = root.findViewById<Button>(R.id.btnCont)
        val list: ListView = root.findViewById(R.id.list)
        val arrayList = ArrayList<String>()
        val myAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, arrayList)
        list.adapter = myAdapter
        
        btnAdd.setOnClickListener {
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

            textViewCalories.text = calories.toString()
            arrayList.add("$food $foodAmount")
            myAdapter.notifyDataSetChanged()

        }

        btnClear.setOnClickListener {
            calories = 0
            textViewCalories.text = calories.toString()
            arrayList.clear()
            myAdapter.notifyDataSetChanged()
        }

        btnContinue.setOnClickListener {
            val two = OutputFragment()

            val transaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.mainLayout, two)
            transaction?.commit()
//            transaction?.addToBackStack(null)
        }

        return root
    }

}