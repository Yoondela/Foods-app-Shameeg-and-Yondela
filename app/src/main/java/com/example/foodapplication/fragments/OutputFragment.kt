package com.example.foodapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplication.R
import com.example.foodapplication.progressDatabase.Calories
import com.example.foodapplication.progressDatabase.CaloriesViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class OutputFragment : Fragment() {
    private lateinit var userViewModel: CaloriesViewModel
    var calories = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.fragment_output, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = requireView()
        var totalCals = root.findViewById<TextView>(R.id.totalCalories)
        var listOfCalories = checkNotNull(arguments?.getDoubleArray("calories"))
        var btnSave = root.findViewById<Button>(R.id.btnAddToDB)
        for(element in listOfCalories){
            calories += element
        }

        totalCals.text = "total amount of calories consumed: ${calories.roundToInt()}"
        userViewModel = ViewModelProvider(this).get(CaloriesViewModel::class.java)
        btnSave.setOnClickListener {
            insertCalsToDatabase()
        }
    }

    private fun insertCalsToDatabase(){

        val uid = checkNotNull(arguments?.getString("UID"))
        val calender = Calendar.getInstance()
        val currentDate = SimpleDateFormat("MMM, d, yyyy").format(calender.time)
        if(calories!= 0.0){
            val cals= Calories(uid, calories.roundToInt(), currentDate)
            userViewModel.addCalories(cals)
            Toast.makeText(requireContext(), "updated correctly", Toast.LENGTH_SHORT).show()
        }
    }
}