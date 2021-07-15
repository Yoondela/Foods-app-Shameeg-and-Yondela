package com.example.foodapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplication.R
import com.example.foodapplication.progressDatabase.Calories
import com.example.foodapplication.progressDatabase.CaloriesViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlin.collections.ArrayList

class ProgressFragment : Fragment() {

    private lateinit var caloriesViewModel:CaloriesViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_progress, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataValuesAndSetChartData()
    }

    private fun getDataValuesAndSetChartData(){

        val email = checkNotNull(arguments?.get("userEmail"))
        val setOfCals = mutableSetOf<Int>()
        caloriesViewModel = ViewModelProvider(this).get(CaloriesViewModel::class.java)
        caloriesViewModel.readUserCalories(email.toString()).observe(viewLifecycleOwner, { userFilteredData ->
            for(calories in userFilteredData) {
                caloriesViewModel.readUserEntryDate(email.toString(), calories.date).observe(viewLifecycleOwner, { dateFilteredData ->
                    if (dateFilteredData.size > 1) {
                        var updatedCals = 0
                        for (items in dateFilteredData) {
                            updatedCals += items.calories
                        }
                        setOfCals.add(updatedCals)
                    }
                    else{
                        var updatedCals=0
                        updatedCals+=calories.calories
                        setOfCals.add(updatedCals)
                    }
                    getUpdatedCals(setOfCals)
                })
            }
        })
    }

    private fun getUpdatedCals(setOfCals: MutableSet<Int>) {

        var dataValues = ArrayList<Entry>()

        setOfCals.forEachIndexed { index, i ->
            dataValues.add(Entry(index.toFloat(),i.toFloat()))
        }
        setChartData(dataValues)
    }

    private fun setChartData(dataValues:ArrayList<Entry>){

        val root = requireView()
        val lineChart = root.findViewById<LineChart>(R.id.lineChart)
        val lineDataSet = LineDataSet(dataValues,"Calories")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)

        val data = LineData(dataSets)

        lineChart.data = data
        lineChart.invalidate()

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = LineChartXAxisValueFormatter()
    }

    inner class LineChartXAxisValueFormatter : IndexAxisValueFormatter(){
        override fun getFormattedValue(value: Float): String {

            return "date"
        }
    }
}
