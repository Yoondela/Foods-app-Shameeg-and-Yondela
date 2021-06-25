package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickLogin()
    }

    private fun onClickLogin(){

        val root = requireView()
        val textRegister = root.findViewById<TextView>(R.id.txtReg)
        val loginBtn = root.findViewById<Button>(R.id.loginBtn)

        textRegister.setOnClickListener{
            gotoRegistration()
        }

        loginBtn.setOnClickListener{
            confirmInputAndGotoFoodInputScreen()
        }
    }

    private fun validateEmail():Boolean{
        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val email = textInputEmail.editText?.text.toString().trim()

        return if(email.isEmpty()){
            textInputEmail.error = "Field cannot be empty"
            false
        } else{
            textInputEmail.error = null
            true
        }
    }

    private fun validatePassword():Boolean{

        val root = requireView()
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val password = textInputPassword.editText?.text.toString().trim()

        return if(password.isEmpty()){
            textInputPassword.error = "Field cannot be empty"
            false
        }else{
            textInputPassword.error = null
            true
        }
    }

    private fun confirmInputAndGotoFoodInputScreen(){

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val email = textInputEmail.editText?.text.toString().trim()
        val password = textInputPassword.editText?.text.toString().trim()

        if(!validateEmail() or !validatePassword()){
            return
        }
        else{
            val dbHandler = DatabaseHandler(requireContext())
            val user = User(email, password)
            if(dbHandler.checkUserDetails(user)){
                gotoInputFrag()
            }
            else{
                Toast.makeText(requireContext(),"incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoInputFrag(){

        val inputFragment = InputFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, inputFragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    private fun gotoRegistration(){

        val registerFragment = RegisterFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, registerFragment)
        transaction.commit()
    }
}