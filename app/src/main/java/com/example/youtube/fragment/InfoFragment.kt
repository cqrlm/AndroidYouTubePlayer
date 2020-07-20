package com.example.youtube.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.youtube.R

class InfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.info_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.tv_info)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.info_title)
        textView.text = resources.getString(R.string.info_text)
        return view
    }
}