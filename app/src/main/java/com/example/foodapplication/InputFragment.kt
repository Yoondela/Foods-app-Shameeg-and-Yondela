package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*

class InputFragment : Fragment(R.layout.fragment_input){

    private var calories = 0.0
    var arrayList = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_input, container, false)
        var communicator: Communicator = activity as Communicator
        val btnAdd: Button = root.findViewById(R.id.btnAdd)
        val btnClear: Button = root.findViewById(R.id.btnClear)
        val btnContinue = root.findViewById<Button>(R.id.btnCont)
        val list: ListView = root.findViewById(R.id.list)
        val myAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, arrayList)
        list.adapter = myAdapter

        btnAdd.setOnClickListener {
            for (i in communicator.getFoodList()){
                if (!arrayList.contains(i)){
                    arrayList.add(i)
                }
            }
            myAdapter.notifyDataSetChanged()
        }

        btnClear.setOnClickListener {
            calories = communicator.resetCals()
            arrayList.clear()
            myAdapter.notifyDataSetChanged()
        }

        btnContinue.setOnClickListener {
            calories = communicator.getJson()
        }

        return root
    }
}