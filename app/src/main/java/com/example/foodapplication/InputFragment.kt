package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*

class InputFragment : Fragment(R.layout.fragment_input){

    private var calories = ""
    var arrayList = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_input, container, false)
        var communicator: Communicator = activity as Communicator
        val editTextFood: EditText = root.findViewById(R.id.txtFood)
        val amount: EditText = root.findViewById(R.id.txtAmount)
        val btnAdd: Button = root.findViewById(R.id.btnAdd)
        val btnClear: Button = root.findViewById(R.id.btnClear)
        val btnContinue = root.findViewById<Button>(R.id.btnCont)
        val list: ListView = root.findViewById(R.id.list)
//        val arrayList = ArrayList<String>()
        val myAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, arrayList)
        list.adapter = myAdapter

        btnAdd.setOnClickListener {
//            communicator.getJson()
            calories = communicator.getJson()
            arrayList.add(editTextFood.text.toString() + " " +  amount.text.toString())
            myAdapter.notifyDataSetChanged()
        }

        btnClear.setOnClickListener {
            calories = communicator.resetCals()
            arrayList.clear()
            myAdapter.notifyDataSetChanged()
        }

        btnContinue.setOnClickListener {
            communicator.passData(calories)
        }

        return root
    }


}