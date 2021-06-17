package com.example.foodapplication

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.grey)))
        if (savedInstanceState == null) {
            setDefaultScreen()
        }
    }

    private fun setDefaultScreen() {

        val loginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, loginFragment)
            commit()
        }
    }

    override fun passData(listOfCalories:ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("calories",listOfCalories)

        val transaction = this.supportFragmentManager.beginTransaction()
        val outputFragment = OutputFragment()

        outputFragment.arguments = bundle
        transaction.replace(R.id.mainLayout, outputFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun goToRegister() {

        val registerFragment = RegisterFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, registerFragment)
            commit()
        }
    }

    override fun goToLogin() {

        val loginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, loginFragment)
            commit()
        }
    }

    override fun goToInputFrag(){

        val inputFragment = InputFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, inputFragment)
            commit()
        }
    }
}

class Data(val items:List<Items>)
class Items(val calories:String)