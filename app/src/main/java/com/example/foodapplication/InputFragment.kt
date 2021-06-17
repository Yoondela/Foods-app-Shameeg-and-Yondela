package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.lang.StringBuilder

class InputFragment : Fragment(R.layout.fragment_input){

    private var calories = ""
    private var query = StringBuilder()
    private var listOfFood = ArrayList<String>()
    var listOfCalories = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_input, container, false)
        val communicator: Communicator = activity as Communicator
        val etFood: EditText = root.findViewById(R.id.txtFood)
        val btnAdd: Button = root.findViewById(R.id.btnAdd)
        val btnClear: Button = root.findViewById(R.id.btnClear)
        val btnContinue = root.findViewById<Button>(R.id.btnCont)
        val list: ListView = root.findViewById(R.id.list)
        val progressBar:ProgressBar = root.findViewById(R.id.progressBar)
        val tvProcessing: TextView = root.findViewById(R.id.process)
        val myAdapter = ArrayAdapter(requireActivity(), R.layout.black_text_list, listOfFood)
        list.adapter = myAdapter

        btnAdd.setOnClickListener {

            listOfFood.add(etFood.text.toString())
            myAdapter.notifyDataSetChanged()
            query.append(etFood.text.toString())
            etFood.text.clear()

        }

        btnClear.setOnClickListener {

            calories = ""
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
                        communicator.passData(listOfCalories)

                    }
                }
            })
        }

        return root
    }
}