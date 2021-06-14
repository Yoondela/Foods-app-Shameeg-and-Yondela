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

        val inputFragment = InputFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, inputFragment)
            commit()
        }
    }

    override fun passData(calories: String) {
        val bundle = Bundle()
        bundle.putString("calories", calories)

        val transaction = this.supportFragmentManager.beginTransaction()
        val outputFragment = OutputFragment()

        outputFragment.arguments = bundle
        transaction.replace(R.id.mainLayout, outputFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

class Data(val items:List<Items>)
class Items(val calories:Double)