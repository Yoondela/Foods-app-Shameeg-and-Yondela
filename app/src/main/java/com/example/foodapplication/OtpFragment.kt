package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class OtpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?=  inflater.inflate(R.layout.fragment_otp, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val root = requireView()
        val submit = root.findViewById<Button>(R.id.submitOTP)

        submit.setOnClickListener {
            checkOTPAndLogin()
        }
    }

    private fun checkOTPAndLogin(){
        val root = requireView()
        val inputOTP = root.findViewById<EditText>(R.id.etOTP)
        val OTP = inputOTP.text.toString()
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
}