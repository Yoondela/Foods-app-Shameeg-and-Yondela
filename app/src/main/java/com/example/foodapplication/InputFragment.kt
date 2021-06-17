package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import com.example.foodapplication.R.id.*
import com.example.foodapplication.R.layout.fragment_input
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.lang.StringBuilder

class InputFragment : Fragment(){

    internal class Data(val items:List<Items>)
    internal class Items(val calories:String)

    private var query = StringBuilder()
    private var listOfFood = ArrayList<String>()
    var listOfCalories = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(fragment_input, container, false)
        val etFood: EditText = root.findViewById(txtFood)
        val btnAdd: Button = root.findViewById(btnAdd)
        val btnClear: Button = root.findViewById(btnClear)
        val btnContinue = root.findViewById<Button>(btnCont)
        val list: ListView = root.findViewById(list)
        val progressBar:ProgressBar = root.findViewById(progressBar)
        val tvProcessing: TextView = root.findViewById(process)
        val myAdapter = ArrayAdapter(requireActivity(), R.layout.black_text_list, listOfFood)
        list.adapter = myAdapter

        btnAdd.setOnClickListener {

            listOfFood.add(etFood.text.toString())
            myAdapter.notifyDataSetChanged()
            etFood.text.clear()

            for (i in listOfFood){
                query.append("$i ")
            }
        }

        btnClear.setOnClickListener {

            query.clear()
            listOfFood.clear()
            listOfCalories.clear()
            myAdapter.notifyDataSetChanged()
        }

        btnContinue.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            tvProcessing.visibility = View.VISIBLE


            val client = OkHttpClient()
            val url = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query= $query"
            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", "d2e7ffc1f5mshfeab8c7bb8fb7d4p1c7c36jsnb23dcbe86dae")
                .addHeader("x-rapidapi-host", "calorieninjas.p.rapidapi.com")
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){

                        val jsonResponse = response.body()?.string()
                        val gson = GsonBuilder().create()
                        val data = gson.fromJson(jsonResponse, Data::class.java)

                        for (element in data.items){
                            listOfCalories.add(element.calories)
                        }
                        query.clear()
                        println(query)
                        passData(listOfCalories)

                    }
                }
            })
        }

        return root
    }

    fun passData(listOfCalories:ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("calories",listOfCalories)

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val outputFragment = OutputFragment()

        outputFragment.arguments = bundle
        transaction.replace(mainLayout, outputFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}