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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.util.*
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
        val mapOfCals = mutableMapOf<Float,Int>()
        caloriesViewModel = ViewModelProvider(this).get(CaloriesViewModel::class.java)
        caloriesViewModel.readUserCalories(email.toString()).observe(viewLifecycleOwner, { userFilteredData ->
            for(calories in userFilteredData) {
                caloriesViewModel.readUserEntryDate(email.toString(), calories.date).observe(viewLifecycleOwner, { dateFilteredData ->
                    if (dateFilteredData.size > 1) {
                        var updatedCals = 0
                        for (items in dateFilteredData) {
                            updatedCals += items.calories
                        }
                        mapOfCals[calories.date] = updatedCals
                    }
                    else{
                        var updatedCals=0
                        updatedCals+=calories.calories
                        mapOfCals[calories.date] = updatedCals
                    }
                    getUpdatedCals(mapOfCals)
                })
            }
        })
    }

    private fun getUpdatedCals(mapOfCals: MutableMap<Float,Int>){

        var dataValues = ArrayList<Entry>()
        for((key,value)in mapOfCals.toSortedMap()){
            dataValues.add(Entry(key,value.toFloat()))
        }
        setChartData(dataValues)
    }

    private fun setChartData(dataValues:ArrayList<Entry>){

        val root = requireView()
        val lineChart = root.findViewById<LineChart>(R.id.lineChart)
        val lineDataSet = LineDataSet(dataValues,"Calories")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)

        //Setting data to lineChart
        val data = LineData(dataSets)
        lineChart.data = data
        lineChart.description = null
        lineChart.legend.isEnabled = false
        lineDataSet.color = requireContext().getColor(R.color.white)
        lineDataSet.setDrawCircles(false)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable = requireContext().getDrawable(R.drawable.chart_gradient)

        //Formatting x-axis
        lineChart.xAxis.axisMaximum = getMaxXValue()
        lineChart.xAxis.axisMinimum = getMinXValue()
        lineChart.xAxis.setCenterAxisLabels(true)
        lineChart.isScaleXEnabled = false
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.valueFormatter = LineChartXAxisValueFormatter()

        //Formatting y-axis
        lineChart.isScaleYEnabled = false
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.isEnabled = false
        lineChart.axisLeft.axisMinimum = 0f
        lineChart.invalidate()
    }

    private fun getMaxXValue():Float{

        val maxDay = Calendar.getInstance()
        maxDay.set(Calendar.HOUR_OF_DAY,0)
        maxDay.set(Calendar.MINUTE,0)
        maxDay.set(Calendar.SECOND,0)
        maxDay.set(Calendar.MILLISECOND,0)
        return maxDay.timeInMillis.toFloat()
    }

    private fun getMinXValue():Float{

        val minDay = Calendar.getInstance()
        minDay.set(Calendar.HOUR_OF_DAY,0)
        minDay.set(Calendar.MINUTE,0)
        minDay.set(Calendar.SECOND,0)
        minDay.set(Calendar.MILLISECOND,0)
        minDay.add(Calendar.DAY_OF_YEAR,-7)
        return minDay.timeInMillis.toFloat()
    }

    inner class LineChartXAxisValueFormatter : IndexAxisValueFormatter(){

        override fun getFormattedValue(value: Float): String {

            return "${SimpleDateFormat("MMM,d").format(value)}"
        }
    }
}
