package com.example.lab6_youtube_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lab6_youtube_app.R

class ThirdPlaylistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.third_playlist_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.playlist_3_title)
        return view
    }
}