package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val communicator = activity as Communicator
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        val logbtn = root.findViewById<TextView>(R.id.txtLogin)
        val registerBtn = root.findViewById<Button>(R.id.registerBtn)

        logbtn.setOnClickListener{
            communicator.goToLogin()
        }

        registerBtn.setOnClickListener{
            communicator.goToLogin()
        }

        return root
    }

}