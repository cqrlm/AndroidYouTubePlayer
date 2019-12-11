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
import kotlinx.android.synthetic.main.playlist_fragment.view.*

class ThirdPlaylistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.playlist_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.playlist_3_title)

        val recyclerView = view.recycler_view
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecyclerAdapter(activity as AppCompatActivity, generateVideoItems())

        return view
    }

    private fun generateVideoItems(): List<YoutubeVideo> {
        return listOf(
            YoutubeVideo("hi5R0gq9tdA"),
            YoutubeVideo("TxAbht2DkyU"),
            YoutubeVideo("HBPQZEU_ofg")
        )
    }
}