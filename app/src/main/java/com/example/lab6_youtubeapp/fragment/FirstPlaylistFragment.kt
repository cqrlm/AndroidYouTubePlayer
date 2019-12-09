package com.example.lab6_youtubeapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lab6_youtubeapp.DeveloperKey
import com.example.lab6_youtubeapp.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX

class FirstPlaylistFragment : Fragment() {
    private lateinit var youTubePlayer: YouTubePlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_playlist_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.playlist_1_title)

        val youTubePlayerFragmentX = YouTubePlayerSupportFragmentX.newInstance()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_fragment, youTubePlayerFragmentX).commit()

        youTubePlayerFragmentX.initialize(
            DeveloperKey.DEVELOPER_KEY,
            object : OnInitializedListener {
                override fun onInitializationSuccess(
                    arg0: YouTubePlayer.Provider?,
                    player: YouTubePlayer,
                    b: Boolean
                ) {
                    if (!b) {
                        youTubePlayer = player
                        youTubePlayer.setFullscreen(true)
                        youTubePlayer.loadVideo("2zNSgSzhBfM")
                        youTubePlayer.play()
                    }
                }

                override fun onInitializationFailure(
                    arg0: YouTubePlayer.Provider?,
                    arg1: YouTubeInitializationResult?
                ) {
                    val error = "ERROR"
                }
            })
        return view
    }
}