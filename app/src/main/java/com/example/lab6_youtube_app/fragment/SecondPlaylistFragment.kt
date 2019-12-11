package com.example.lab6_youtube_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab6_youtube_app.R
import com.example.lab6_youtube_app.recyclerview.RecyclerAdapter
import com.example.lab6_youtube_app.recyclerview.YoutubeVideo
import kotlinx.android.synthetic.main.first_playlist_fragment.view.*
import kotlinx.android.synthetic.main.second_playlist_fragment.view.*

class SecondPlaylistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.second_playlist_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.playlist_2_title)

        val recyclerView = view.recycler_view_second
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecyclerAdapter(activity as AppCompatActivity, generateVideoItems())

        return view
    }

    private fun generateVideoItems(): List<YoutubeVideo> {
        return listOf(
            YoutubeVideo("ZDS4diFfBmQ"),
            YoutubeVideo("M3Udfu6qidk"),
            YoutubeVideo("FOn64iqlphk")
        )
    }
}