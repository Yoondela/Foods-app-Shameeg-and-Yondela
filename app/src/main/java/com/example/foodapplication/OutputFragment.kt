package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.TextView
import com.example.foodapplication.R.id.totalCalories
import kotlin.math.roundToInt

class OutputFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_output, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        var totalCals = requireView().findViewById<TextView>(totalCalories)
        var listOfCalories = requireNotNull(arguments?.getDoubleArray("calories"))

        val calories  = listOfCalories.sum().roundToInt()

        totalCals.text = "Total amount of calories consumed:\n$calories"
    }
}