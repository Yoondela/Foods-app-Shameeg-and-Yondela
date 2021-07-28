package com.example.foodapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.foodapplication.R
import com.example.foodapplication.userDatabase.User
import com.example.foodapplication.userDatabase.DatabaseHandler

class OtpFragment : Fragment(),TextWatcher {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?=  inflater.inflate(
        R.layout.fragment_otp, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val root = requireView()
        val etOTP1 = root.findViewById<EditText>(R.id.etOTP1)
        val etOTP2 = root.findViewById<EditText>(R.id.etOTP2)
        val etOTP3 = root.findViewById<EditText>(R.id.etOTP3)
        val etOTP4 = root.findViewById<EditText>(R.id.etOTP4)
        etOTP1.addTextChangedListener(this)
        etOTP2.addTextChangedListener(this)
        etOTP3.addTextChangedListener(this)
        etOTP4.addTextChangedListener(this)
        etOTP1.requestFocus()
    }

    private fun checkOTPAndLogin(){
        val root = requireView()
        val etOTP1 = root.findViewById<EditText>(R.id.etOTP1)
        val etOTP2 = root.findViewById<EditText>(R.id.etOTP2)
        val etOTP3 = root.findViewById<EditText>(R.id.etOTP3)
        val etOTP4 = root.findViewById<EditText>(R.id.etOTP4)
        val OTP = "${etOTP1.text}${etOTP2.text}${etOTP3.text}${etOTP4.text}"
        val dbHandler = DatabaseHandler(requireContext())
        val user = User(OTP)

        if (dbHandler.checkOTP(user)){
            gotoLogin()
        }
        else{
            Toast.makeText(requireContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show()
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
        checkIfFieldIsFull()
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun checkIfFieldIsFull() {

        val root = requireView()
        val etOTP1 = root.findViewById<EditText>(R.id.etOTP1)
        val etOTP2 = root.findViewById<EditText>(R.id.etOTP2)
        val etOTP3 = root.findViewById<EditText>(R.id.etOTP3)
        val etOTP4 = root.findViewById<EditText>(R.id.etOTP4)

        if (etOTP1.text.isNotEmpty()) {
            etOTP2.requestFocus()
        }
        if (etOTP2.text.isNotEmpty()) {
            etOTP3.requestFocus()
        }
        if (etOTP3.text.isNotEmpty()) {
            etOTP4.requestFocus()
        }
        if (etOTP4.text.isNotEmpty()) {
            checkOTPAndLogin()
        }
    }
}