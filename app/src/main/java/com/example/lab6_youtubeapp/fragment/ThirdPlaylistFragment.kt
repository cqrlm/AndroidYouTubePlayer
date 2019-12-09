package com.example.lab6_youtubeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lab6_youtubeapp.R

class ThirdPlaylistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.third_playlist_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.tv_playlist_3)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.playlist_3_title)
        textView.text = resources.getString(R.string.playlist_3_text)
        return view
    }
}