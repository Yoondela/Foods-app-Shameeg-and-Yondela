package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gotoLogin()
    }

    private fun gotoLogin(){

        val root = requireView()
        val textLogin = root.findViewById<TextView>(R.id.txtLogin)
        val btnRegister = root.findViewById<Button>(R.id.registerBtn)
        val loginFragment = LoginFragment()

        btnRegister.setOnClickListener {

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainLayout, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        textLogin.setOnClickListener{

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainLayout, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}