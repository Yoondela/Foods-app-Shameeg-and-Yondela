package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), Communicator {

    private var calories = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    override fun checkCals(response: Response) {
        if(response.isSuccessful){
            val jsonResponse = response.body()?.string()
            val gson = GsonBuilder().create()
            val data = gson.fromJson(jsonResponse, Data::class.java)
//            val items = gson.fromJson(jsonResponse, Items::class.java)

            for (element in data.items){
                calories = element.calories
            }
        }
    }

    override fun resetCals():String{
        calories = ""

        return calories
    }

    override fun getJson():String{

        val editTextFood: EditText = findViewById(R.id.txtFood)
//        val amount: EditText = findViewById(R.id.txtAmount)
        val query = editTextFood.text.toString()
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
                checkCals(response)
            }
        })

        return calories
    }
}

class Data(val items:List<Items>)
class Items(val calories:String)