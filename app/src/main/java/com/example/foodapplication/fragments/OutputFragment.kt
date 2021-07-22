package com.example.foodapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplication.R
import com.example.foodapplication.progressDatabase.Calories
import com.example.foodapplication.progressDatabase.CaloriesViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class OutputFragment : Fragment() {
    private lateinit var caloriesViewModel: CaloriesViewModel
    var calories = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.fragment_output, container, false)

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.show_prog_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.showProg -> gotoProgressFrag()
        }
        return super.onOptionsItemSelected(item)
    }
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
        caloriesViewModel = ViewModelProvider(this).get(CaloriesViewModel::class.java)
        btnSave.setOnClickListener {
            insertCalsToDatabase()
        }
    }

    private fun insertCalsToDatabase(){

        val userEmail = checkNotNull(arguments?.getString("userEmail"))
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY,0)
        calender.set(Calendar.MINUTE,0)
        calender.set(Calendar.SECOND,0)
        calender.set(Calendar.MILLISECOND,0)
        val currentDate = calender.timeInMillis
        val DBcals = Calories(0,userEmail,calories.roundToInt(),currentDate.toFloat())
        caloriesViewModel.addCalories(DBcals)
        Toast.makeText(requireContext(), "updated successfully", Toast.LENGTH_SHORT).show()
    }

    private fun gotoProgressFrag(){

        val progressFragment = ProgressFragment()
        val bundle=Bundle()
        val userEmail = checkNotNull(arguments?.getString("userEmail"))
        bundle.putString("userEmail", userEmail)
        progressFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainLayout, progressFragment).addToBackStack(null).commit()
    }
}