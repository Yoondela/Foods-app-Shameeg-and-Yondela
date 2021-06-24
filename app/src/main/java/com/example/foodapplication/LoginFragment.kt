package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gotoRegisterOrProgramme()
    }

    private fun gotoRegisterOrProgramme(){

        val root = requireView()
        val textRegister = root.findViewById<TextView>(R.id.txtReg)
        val loginBtn = root.findViewById<Button>(R.id.loginBtn)
        val username = arguments?.getString("name")
        val email = root.findViewById<EditText>(R.id.loginEmail)
        val password = root.findViewById<EditText>(R.id.loginPassword)
        val registerFragment = RegisterFragment()
        val inputFragment = InputFragment()

        textRegister.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainLayout, registerFragment)
            transaction.commit()
        }

        loginBtn.setOnClickListener{

            val dbHandler = DatabaseHandler(requireContext())
            val user = User(username.toString(), email.text.toString(), password.text.toString())
            if(dbHandler.checkUserDetails(user)){
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.mainLayout, inputFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else{
                Toast.makeText(requireContext(),"incorrect details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}