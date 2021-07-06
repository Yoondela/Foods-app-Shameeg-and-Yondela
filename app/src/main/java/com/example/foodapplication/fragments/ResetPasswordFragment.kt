package com.example.foodapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.foodapplication.R
import com.example.foodapplication.userDatabase.User
import com.example.foodapplication.userDatabase.DatabaseHandler
import com.google.android.material.textfield.TextInputLayout

class ResetPasswordFragment : Fragment(), TextWatcher {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.fragment_reset_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = requireView()
        val btnReset = root.findViewById<Button>(R.id.resetBtn)
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.fpEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.resetPassword)
        val textInputConfirmPassword = root.findViewById<TextInputLayout>(R.id.confirmPassword)
        val email = textInputEmail.editText
        val password = textInputPassword.editText
        val rePassword = textInputConfirmPassword.editText

        email?.addTextChangedListener(this)
        password?.addTextChangedListener(this)
        rePassword?.addTextChangedListener(this)

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
        } else if(!PASSWORD_PATTERN.matcher(password).matches()){
            textInputPassword.error = "Password is too weak"
            false
        } else {
            textInputPassword.error = null
            true
        }
    }

    private fun validateConfirmPassword(): Boolean {

        val root = requireView()
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.resetPassword)
        val password = textInputPassword.editText?.text.toString().trim()
        val textInputConfirmPassword = root.findViewById<TextInputLayout>(R.id.confirmPassword)
        val rePassword = textInputConfirmPassword.editText?.text.toString().trim()

        return when {
            rePassword.isEmpty() -> {
                textInputConfirmPassword.error = "Field cannot be empty"
                false
            }
            password != rePassword -> {
                textInputConfirmPassword.error = "Password does not match"
                false
            }
            else -> {
                textInputConfirmPassword.error = null
                true
            }
        }
    }

    private fun gotoLogin(){
        val loginFragment = LoginFragment()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, loginFragment)
        transaction.commit()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        checkIfTextChanged()
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun checkIfTextChanged(){

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.fpEmail)
        val email = textInputEmail.editText?.text.toString().trim()
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.resetPassword)
        val password = textInputPassword.editText?.text.toString().trim()
        val textInputConfirmPassword = root.findViewById<TextInputLayout>(R.id.confirmPassword)
        val rePassword = textInputConfirmPassword.editText?.text.toString().trim()

        if(textInputEmail.error == "Field cannot be empty" && email.isNotEmpty()){
            textInputEmail.error = null
        }
        if(textInputEmail.error == "Email is not in the correct format" && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.error = null
        }
        if(textInputPassword.error == "Field cannot be empty" && password.isNotEmpty()){
            textInputPassword.error = null
        }
        if(textInputPassword.error == "Password is too weak" && PASSWORD_PATTERN.matcher(password).matches()){
            textInputPassword.error = null
        }
        if(textInputConfirmPassword.error == "Field cannot be empty" && rePassword.isNotEmpty()){
            textInputConfirmPassword.error = null
        }
        if (textInputConfirmPassword.error == "Password does not match" && password == rePassword){
            textInputConfirmPassword.error = null
        }
    }
}