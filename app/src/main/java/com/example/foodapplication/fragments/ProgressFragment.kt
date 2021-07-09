package com.example.foodapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplication.R
import com.example.foodapplication.progressDatabase.CaloriesViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

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

        var dataValues = ArrayList<Entry>()
        val email = checkNotNull(arguments?.get("userEmail"))
        caloriesViewModel = ViewModelProvider(this).get(CaloriesViewModel::class.java)
        caloriesViewModel.readUserCalories(email.toString()).observe(viewLifecycleOwner, { calories ->
            calories.forEachIndexed { index, calories ->
                dataValues.add(Entry(index.toFloat(),calories.calories.toFloat()))
                setChartData(dataValues)
            }
        })
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

    class LineChartXAxisValueFormatter : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {

            return "date"
        }
    }
}