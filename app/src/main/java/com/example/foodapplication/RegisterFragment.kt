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
        val name = root.findViewById<EditText>(R.id.fullName)
        val email = root.findViewById<EditText>(R.id.registerEmail)
        val password = root.findViewById<EditText>(R.id.registerPassword)

        val loginFragment = LoginFragment()

        btnRegister.setOnClickListener {

            if (name.text.toString().isNotEmpty()&&
                    email.text.toString().isNotEmpty()&&
                    password.text.toString().isNotEmpty()
            ){
                val user =
                    User(name.text.toString(), email.text.toString(), password.text.toString())
                var dbHandler = DatabaseHandler(requireContext())
                dbHandler.storeUserDetails(user)
                sendUserNameToLogin(user)
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.mainLayout, loginFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else{
                Toast.makeText(requireContext(),"please fill in all fields",Toast.LENGTH_SHORT).show()
            }
        }

        textLogin.setOnClickListener{

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainLayout, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun sendUserNameToLogin(user:User){

        val bundle = Bundle()
        val loginFrag = LoginFragment()

        bundle.putString("name",user.name)
        loginFrag.arguments = bundle
    }
}