package com.example.foodapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.TextView
import com.example.foodapplication.R
import kotlin.math.roundToInt

class OutputFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.fragment_output, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = requireView()
        var totalCals = root.findViewById<TextView>(R.id.totalCalories)
        var listOfCalories = checkNotNull(arguments?.getDoubleArray("calories"))
        var calories = 0.0
        for(element in listOfCalories){
            calories += element
        }

        totalCals.text = "total amount of calories consumed: ${calories.roundToInt()}"

    }

}