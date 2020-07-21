package com.example.youtube.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.R
import com.example.youtube.recyclerview.YoutubeVideo

class SecondPlaylistFragment : PlaylistFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.playlist_2_title)
        super.onCreate(savedInstanceState)
    }

    override fun generateVideoItems(): List<YoutubeVideo> {
        return listOf(
            YoutubeVideo("ZDS4diFfBmQ"),
            YoutubeVideo("M3Udfu6qidk"),
            YoutubeVideo("FOn64iqlphk")
        )
    }
}