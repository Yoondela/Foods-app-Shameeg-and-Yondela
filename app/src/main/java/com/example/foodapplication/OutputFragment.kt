package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.TextView

class OutputFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_output, container, false)
        var totalCals = root.findViewById<TextView>(R.id.totalCalories)
        var listOfCalories: ArrayList<String> = arguments?.getStringArrayList("calories")!!
        var calories = 0.0
        for(element in listOfCalories){
            calories += element.toDouble()
        }

        totalCals.text = "total amount of calories consumed: $calories"

        return root
    }
}