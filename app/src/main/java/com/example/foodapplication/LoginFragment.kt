package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val communicator:Communicator = activity as Communicator
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val regbtn = root.findViewById<TextView>(R.id.txtReg)
        val loginBtn = root.findViewById<Button>(R.id.loginBtn)

        regbtn.setOnClickListener{
            communicator.goToRegister()
        }

        loginBtn.setOnClickListener {
            communicator.goToInputFrag()
        }


        return root
    }
}