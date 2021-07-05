package com.example.foodapplication

import DatabaseHandler
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(),TextWatcher {

    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val email = textInputEmail.editText
        val password = textInputPassword.editText

        email?.addTextChangedListener(this)
        password?.addTextChangedListener(this)

        onClickLogin()

        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)
        if (isRemembered){
            gotoInputFrag()
        }
    }

    private fun onClickLogin(){

        val root = requireView()
        val textRegister = root.findViewById<TextView>(R.id.txtReg)
        val loginBtn = root.findViewById<Button>(R.id.loginBtn)
        val forgotPassword = root.findViewById<TextView>(R.id.forgotPassword)

        textRegister.setOnClickListener{
            gotoRegistration()
        }

        loginBtn.setOnClickListener{
            confirmInputAndGotoFoodInputScreen()
        }

        forgotPassword.setOnClickListener{
            gotoResetPasswordFragment()
        }
    }

    private fun validateEmail():Boolean{
        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
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
        val cbRememberMe = root.findViewById<CheckBox>(R.id.rememberMeCB)
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
                val checked: Boolean = cbRememberMe.isChecked
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("CHECKBOX", checked)
                editor.apply()
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
        transaction.commit()

    }

    private fun gotoRegistration(){

        val registerFragment = RegisterFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, registerFragment)
        transaction.commit()
    }

    private fun gotoResetPasswordFragment(){

        val resetPasswordFragment = ResetPasswordFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, resetPasswordFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        checkIfTextHasChanged()
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun checkIfTextHasChanged(){

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val email = textInputEmail.editText?.text.toString().trim()
        val password = textInputPassword.editText?.text.toString().trim()

        if(textInputEmail.error == "Field cannot be empty" && email.isNotEmpty()){
            textInputEmail.error = null
        }
        else if(textInputEmail.error == "Email is not in the correct format" && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.error = null
        }
        else if(textInputPassword.error == "Field cannot be empty" && password.isNotEmpty()){
            textInputPassword.error = null
        }
    }
}