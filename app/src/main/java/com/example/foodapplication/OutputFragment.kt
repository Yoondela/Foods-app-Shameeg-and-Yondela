package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.TextView
import com.example.foodapplication.R.id.totalCalories

class OutputFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_output, container, false)
        var totalCals = root.findViewById<TextView>(totalCalories)
        var listOfCalories = requireNotNull(arguments?.getStringArrayList("calories"))

        var calories = 0.0
        for(element in listOfCalories){
            calories += element.toDouble()
        }

        totalCals.text = "Total amount of calories consumed\n$calories"

        return root
    }
}