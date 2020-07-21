package com.example.youtube.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.R
import com.example.youtube.recyclerview.YoutubeVideo

class FirstPlaylistFragment : PlaylistFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.playlist_1_title)
        super.onCreate(savedInstanceState)
    }

    override fun generateVideoItems(): List<YoutubeVideo> {
        return listOf(
            YoutubeVideo("3kSdNmkcmTE"),
            YoutubeVideo("YB8IVbvdoW0"),
            YoutubeVideo("_mgfpB5XXEs")
        )
    }
}