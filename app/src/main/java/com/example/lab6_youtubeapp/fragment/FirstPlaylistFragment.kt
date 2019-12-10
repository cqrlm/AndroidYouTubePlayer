package com.example.lab6_youtubeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab6_youtubeapp.R
import com.example.lab6_youtubeapp.recyclerview.RecyclerAdapter
import com.example.lab6_youtubeapp.recyclerview.VideoItem
import kotlinx.android.synthetic.main.first_playlist_fragment.view.*

class FirstPlaylistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_playlist_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.playlist_1_title)

        val recyclerView = view.recycler_view
//        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecyclerAdapter(activity as AppCompatActivity, generateVideoItems())

        return view
    }

    private fun generateVideoItems() : List<VideoItem> {
        return listOf(
            VideoItem("P3mAtvs5Elc"),
            VideoItem("nCgQDjiotG0"),
            VideoItem("3kSdNmkcmTE")
        )
    }
}