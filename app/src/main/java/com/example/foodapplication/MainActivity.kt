package com.example.foodapplication

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), Communicator {

    private var calories = 0.0
    var listOfFood = ArrayList<String>()
    var query = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.grey)))
        if (savedInstanceState == null) {
            setDefaultScreen()
        }
    }

    private fun setDefaultScreen() {

        val inputFragment = InputFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, inputFragment)
            commit()
        }
    }

    override fun passData(calories: String) {
        val bundle = Bundle()
        bundle.putString("calories", calories)

        val transaction = this.supportFragmentManager.beginTransaction()
        val outputFragment = OutputFragment()

        outputFragment.arguments = bundle
        transaction.replace(R.id.mainLayout, outputFragment)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun resetCals(){
        query.clear()
        listOfFood.clear()
    }

    override fun getJson():Double{

        for (i in listOfFood){
            query.append("$i ")
        }

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
                        calories += element.calories
                    }
                    passData(calories.toString())
                }
            }
        })

        return calories
    }

    override fun getFoodList():ArrayList<String>{

        val etFood = findViewById<EditText>(R.id.txtFood)

        listOfFood.add(etFood.text.toString())

        return listOfFood
    }
}

class Data(val items:List<Items>)
class Items(val calories:Double)