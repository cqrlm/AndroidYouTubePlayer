package com.example.youtube.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.R
import com.example.youtube.recyclerview.YoutubeVideo

class ThirdPlaylistFragment : PlaylistFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.playlist_3_title)
        super.onCreate(savedInstanceState)
    }

    override fun generateVideoItems(): List<YoutubeVideo> {
        return listOf(
            YoutubeVideo("hi5R0gq9tdA"),
            YoutubeVideo("TxAbht2DkyU"),
            YoutubeVideo("HBPQZEU_ofg")
        )
    }
}