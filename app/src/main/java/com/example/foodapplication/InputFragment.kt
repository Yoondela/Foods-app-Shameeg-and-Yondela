package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import org.w3c.dom.Text

class InputFragment : Fragment(R.layout.fragment_input){

    private var calories = 0.0
    var arrayList = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_input, container, false)
        var communicator: Communicator = activity as Communicator
        var etFood: EditText = root.findViewById(R.id.txtFood)
        val btnAdd: Button = root.findViewById(R.id.btnAdd)
        val btnClear: Button = root.findViewById(R.id.btnClear)
        val btnContinue = root.findViewById<Button>(R.id.btnCont)
        val list: ListView = root.findViewById(R.id.list)
        val progressBar:ProgressBar = root.findViewById(R.id.progressBar)
        val tvProcessing: TextView = root.findViewById(R.id.process)
        val myAdapter = ArrayAdapter(requireActivity(), R.layout.black_text_list, arrayList)
        list.adapter = myAdapter

        btnAdd.setOnClickListener {
            for (i in communicator.getFoodList()){
                if (!arrayList.contains(i)){
                    arrayList.add(i)
                }
            }
            myAdapter.notifyDataSetChanged()
            etFood.text.clear()
        }

        btnClear.setOnClickListener {
            communicator.resetCals()
            arrayList.clear()
            myAdapter.notifyDataSetChanged()
        }

        btnContinue.setOnClickListener {
            calories = communicator.getJson()
            progressBar.visibility = View.VISIBLE
            tvProcessing.visibility = View.VISIBLE
        }

        return root
    }
}