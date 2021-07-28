package com.example.foodapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.foodapplication.R
import com.example.foodapplication.userDatabase.User
import com.example.foodapplication.emailVerification.OTPSender
import com.example.foodapplication.userDatabase.DatabaseHandler
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import java.util.regex.Pattern

val PASSWORD_PATTERN: Pattern = Pattern.compile("^" +
        "(?=.*[0-9])" +
        "(?=.*[a-z])" +
        "(?=.*[A-Z])" +
        ".{5,}" +
        "$")

class RegisterFragment : Fragment(),TextWatcher {

    private val random = Random()
    private val OTP = random.nextInt(8999)+1000
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.registerEmail)
        val email = textInputEmail.editText
        val textInputUsername = root.findViewById<TextInputLayout>(R.id.registerUsername)
        val username = textInputUsername.editText
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.registerPassword)
        val password = textInputPassword.editText
//        val callback = object: OnBackPressedCallback(true){
//            override fun handleOnBackPressed(){
//
//            }
//        }
        email?.addTextChangedListener(this)
        username?.addTextChangedListener(this)
        password?.addTextChangedListener(this)

        onClickRegister()

    }

    private fun onClickRegister(){

        val root = requireView()
        val textLogin = root.findViewById<TextView>(R.id.txtLogin)
        val btnRegister = root.findViewById<Button>(R.id.registerBtn)

        btnRegister.setOnClickListener {
            confirmInputAndStoreUserDetailsInDB()
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
        } else if(!EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.error = "Email is not in the correct format"
            false
        }else{
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
        } else if(!PASSWORD_PATTERN.matcher(password).matches()){
            textInputPassword.error = "Password is too weak"
            false
        }else{
            textInputPassword.error = null
            true
        }
    }

    private fun confirmInputAndStoreUserDetailsInDB() {

        val root = requireView()
        val name = root.findViewById<TextInputLayout>(R.id.registerUsername)
        val email = root.findViewById<TextInputLayout>(R.id.registerEmail)
        val password = root.findViewById<TextInputLayout>(R.id.registerPassword)

        if (!validateEmail() or !validateUsername() or !validatePassword()) {
            return
        } else{
            val user =
                User(name.editText?.text.toString(), email.editText?.text.toString(), password.editText?.text.toString(),OTP.toString())
            val dbHandler = DatabaseHandler(requireContext())
            if(dbHandler.storeUserDetails(user)){
                sendOTP(user)
            }
        }
    }

    private fun gotoLogin(){

        val loginFragment = LoginFragment()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun sendOTP(user: User){

        val root = requireView()
        val email = root.findViewById<TextInputLayout>(R.id.registerEmail)

        val sender = Thread {
            try {
                val sender = OTPSender("syfoodapp@gmail.com", "SSYS400D")
                sender.sendMail(
                    "OTP",
                    "Your OTP is: ${user.OTP}",
                    "syfoodapp@gmail.com",
                    email.editText?.text.toString().trim()
                )
                gotoOTPScreen()
            } catch (e: Exception) {
                println("failed")
                Toast.makeText(requireContext(),"Invalid email address",Toast.LENGTH_SHORT).show()
            }
        }
        sender.start()
    }

    private fun gotoOTPScreen(){

        val otpFragment = OtpFragment()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, otpFragment)
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
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.registerEmail)
        val email = textInputEmail.editText?.text.toString().trim()
        val name = root.findViewById<TextInputLayout>(R.id.registerUsername)
        val username = name.editText?.text.toString().trim()
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.registerPassword)
        val password = textInputPassword.editText?.text.toString().trim()

        if(textInputEmail.error == "Field cannot be empty" && email.isNotEmpty()){
            textInputEmail.error = null
        }
        else if(textInputEmail.error == "Email is not in the correct format" && EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.error = null
        }

        else if(name.error == "Field cannot be empty" && username.isNotEmpty()){
            name.error = null
        }

        else if(textInputPassword.error == "Field cannot be empty" && password.isNotEmpty()){
            textInputPassword.error = null
        }
        else if(textInputPassword.error == "Password is too weak" && PASSWORD_PATTERN.matcher(password).matches()){
            textInputPassword.error = null
        }
    }
}