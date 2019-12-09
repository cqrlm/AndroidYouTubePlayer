package com.example.lab6_youtubeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lab6_youtubeapp.R

class SecondPlaylistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.second_playlist_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.playlist_2_title)
        return view
    }
}