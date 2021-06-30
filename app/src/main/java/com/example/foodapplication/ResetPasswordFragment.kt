package com.example.foodapplication

import DatabaseHandler
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class ResetPasswordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_reset_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = requireView()
        val btnReset = root.findViewById<Button>(R.id.resetBtn)

        btnReset.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.fpEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.resetPassword)
        val email = textInputEmail.editText?.text.toString().trim()
        val password = textInputPassword.editText?.text.toString().trim()

        if (!validatePassword() or !validateConfirmPassword() or !validateEmail()) {
            return
        }
        else{
            val user = User(email,password)
            val dbHandler = DatabaseHandler(requireContext())
            if(dbHandler.resetPassword(user)) {
                gotoLogin()
            }
        }
    }

    private fun validateEmail():Boolean{
        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.fpEmail)
        val email = textInputEmail.editText?.text.toString().trim()

        return if(email.isEmpty()){
            textInputEmail.error = "Field cannot be empty"
            false
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.error = "Email is not in the correct format"
            false
        } else{
            textInputEmail.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val root = requireView()

        val textInputPassword = root.findViewById<TextInputLayout>(R.id.resetPassword)
        val password = textInputPassword.editText?.text.toString().trim()

        return if (password.isEmpty()) {
            textInputPassword.error = "Field cannot be empty"
            false
        } else {
            textInputPassword.error = null
            true
        }

        return true
    }

    private fun validateConfirmPassword(): Boolean {

        val root = requireView()
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.resetPassword)
        val password = textInputPassword.editText?.text.toString().trim()
        val textInputConfirmPassword = root.findViewById<TextInputLayout>(R.id.confirmPassword)
        val rePassword = textInputConfirmPassword.editText?.text.toString().trim()

        when {
            rePassword.isEmpty() -> {
                textInputConfirmPassword.error = "Field cannot be empty"
                return false
            }
            password != rePassword -> {
                textInputConfirmPassword.error = "Password does not match"
                false
            }
            else -> {
                textInputConfirmPassword.error = null
            }
        }
        return true
    }

    private fun gotoLogin(){
        val loginFragment = LoginFragment()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, loginFragment)
        transaction.commit()
    }
}