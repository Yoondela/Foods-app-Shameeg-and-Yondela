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
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickRegister()
    }

    private fun onClickRegister(){

        val root = requireView()
        val textLogin = root.findViewById<TextView>(R.id.txtLogin)
        val btnRegister = root.findViewById<Button>(R.id.registerBtn)

        btnRegister.setOnClickListener {
            confirmInputAndGotoLogin()
        }

        textLogin.setOnClickListener{
            gotoLogin()
        }
    }

    private fun validateUsername():Boolean{

        val root = requireView()

        val name = root.findViewById<TextInputLayout>(R.id.registerUsername)
        val username = name.editText?.text.toString().trim()

        return when {
            username.isEmpty() -> {
                name.error = "Field cannot be empty"
                false
            }
            username.length > 20 -> {
                name.error = "Username cannot exceed 20 characters"
                false
            }
            else -> {
                name.error = null
                true
            }
        }
    }

    private fun validateEmail():Boolean{
        val root = requireView()

        val textInputEmail = root.findViewById<TextInputLayout>(R.id.registerEmail)
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

        val textInputPassword = root.findViewById<TextInputLayout>(R.id.registerPassword)
        val password = textInputPassword.editText?.text.toString().trim()

        return if(password.isEmpty()){
            textInputPassword.error = "Field cannot be empty"
            false
        } else{
            textInputPassword.error = null
            true
        }
    }

    private fun confirmInputAndGotoLogin() {

        val root = requireView()
        val name = root.findViewById<TextInputLayout>(R.id.registerUsername)
        val email = root.findViewById<TextInputLayout>(R.id.registerEmail)
        val password = root.findViewById<TextInputLayout>(R.id.registerPassword)

        if (!validateEmail() or !validateUsername() or !validatePassword()) {
            return
        } else{
            val user =
                User(name.editText?.text.toString(), email.editText?.text.toString(), password.editText?.text.toString())
            var dbHandler = DatabaseHandler(requireContext())
            dbHandler.storeUserDetails(user)
            gotoLogin()
        }
    }

    private fun gotoLogin(){

        val loginFragment = LoginFragment()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}