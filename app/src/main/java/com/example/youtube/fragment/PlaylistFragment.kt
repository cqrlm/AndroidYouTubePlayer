package com.example.youtube.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.R
import com.example.youtube.recyclerview.RecyclerAdapter
import com.example.youtube.recyclerview.YoutubeVideo
import kotlinx.android.synthetic.main.playlist_fragment.view.*

abstract class PlaylistFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.playlist_fragment, container, false)

        val recyclerView = view.recycler_view
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecyclerAdapter(activity as AppCompatActivity, generateVideoItems())

        return view
    }

    abstract fun generateVideoItems(): List<YoutubeVideo>
}