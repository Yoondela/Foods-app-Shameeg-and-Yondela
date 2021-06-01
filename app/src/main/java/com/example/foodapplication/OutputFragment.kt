package com.example.foodapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*

class OutputFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_output, container, false)
    }
}