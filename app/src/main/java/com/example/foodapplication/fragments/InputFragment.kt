package com.example.foodapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import androidx.annotation.Nullable
import com.example.foodapplication.R
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.lang.StringBuilder

class InputFragment : Fragment(), Callback {

    lateinit var preferences: SharedPreferences

    private var calories = 0.0
    private var query = StringBuilder()
    private var listOfFood = ArrayList<String>()
    private var listOfCalories = ArrayList<Double>()
    private val client = OkHttpClient()
    inner class Data(val items: List<Items>)
    inner class Items(val calories: Double)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_input, container, false)

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.for_menu_file, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logoutMenuItem -> executeLogout()
            R.id.showProg -> gotoProgressFrag()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickAllButtons()

        preferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

    }

    private fun makeNetworkCall() {

        val url = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query= $query"
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("x-rapidapi-key", "d2e7ffc1f5mshfeab8c7bb8fb7d4p1c7c36jsnb23dcbe86dae")
            .addHeader("x-rapidapi-host", "calorieninjas.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
    }

    override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful) {

            val jsonResponse = response.body()?.string()
            val gson = GsonBuilder().create()
            val data = gson.fromJson(jsonResponse, Data::class.java)

            for (element in data.items) {
                listOfCalories.add(element.calories)
            }
            query.clear()
            passData()
        }
    }

    private fun passData() {
        val bundle = Bundle()
        bundle.putDoubleArray("calories", listOfCalories.toDoubleArray())
        val userEmail = checkNotNull(arguments?.getString("userEmail"))
        bundle.putString("userEmail", userEmail)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val outputFragment = OutputFragment()

        outputFragment.arguments = bundle
        transaction.replace(R.id.mainLayout, outputFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onClickAllButtons() {

        val root = requireView()
        val etFood: EditText = root.findViewById(R.id.txtFood)
        val btnAdd: Button = root.findViewById(R.id.btnAdd)
        val btnClear: Button = root.findViewById(R.id.btnClear)
        val btnContinue = root.findViewById<Button>(R.id.btnCont)
        val progressBar: ProgressBar = root.findViewById(R.id.progressBar)
        val tvProcessing: TextView = root.findViewById(R.id.process)
        val list: ListView = root.findViewById(R.id.list)
        val myAdapter = ArrayAdapter(requireActivity(), R.layout.black_text_list, listOfFood)
        list.adapter = myAdapter

        btnAdd.setOnClickListener {

            listOfFood.add(etFood.text.toString())
            myAdapter.notifyDataSetChanged()
            query.append(etFood.text.toString())
            etFood.text.clear()
        }

        btnClear.setOnClickListener {

            calories = 0.0
            query.clear()
            listOfFood.clear()
            listOfCalories.clear()
            myAdapter.notifyDataSetChanged()
        }

        btnContinue.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            tvProcessing.visibility = View.VISIBLE
            makeNetworkCall()
        }
    }

    private fun executeLogout(){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
        val loginFragment = LoginFragment()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainLayout, loginFragment).commit()
        Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_LONG).show()
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