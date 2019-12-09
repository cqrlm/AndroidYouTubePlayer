package com.example.lab6_youtubeapp.recyclerview

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lab6_youtubeapp.DeveloperKey
import com.example.lab6_youtubeapp.R
import com.google.android.youtube.player.*

class RecyclerAdapter(var ctx: Context) :
    RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder>() {
    //these ids are the unique id for each video
    var videoID =
        arrayOf("P3mAtvs5Elc", "nCgQDjiotG0", "3kSdNmkcmTE")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoInfoHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoInfoHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: VideoInfoHolder,
        position: Int
    ) {
        val onThumbnailLoadedListener: YouTubeThumbnailLoader.OnThumbnailLoadedListener =
            object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                override fun onThumbnailError(
                    youTubeThumbnailView: YouTubeThumbnailView?,
                    errorReason: YouTubeThumbnailLoader.ErrorReason?
                ) {
                    // TODO
                }

                override fun onThumbnailLoaded(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    s: String?
                ) {
                    youTubeThumbnailView.visibility = View.VISIBLE
                    holder.relativeLayoutOverYouTubeThumbnailView.visibility = View.VISIBLE
                }
            }
        holder.youTubeThumbnailView.initialize(
            DeveloperKey.DEVELOPER_KEY,
            object : YouTubeThumbnailView.OnInitializedListener {
                override fun onInitializationSuccess(
                    youTubeThumbnailView: YouTubeThumbnailView?,
                    youTubeThumbnailLoader: YouTubeThumbnailLoader
                ) {
                    youTubeThumbnailLoader.setVideo(videoID[position])
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener)
                }

                override fun onInitializationFailure(
                    youTubeThumbnailView: YouTubeThumbnailView?,
                    youTubeInitializationResult: YouTubeInitializationResult?
                ) { //write something for failure
                }
            })
        holder.playButton.setOnClickListener {
            // val youTubePlayerFragmentX = YouTubePlayerSupportFragmentX.newInstance()
            val youTubePlayerFragment = YouTubePlayerFragment.newInstance()

            (ctx as Activity).fragmentManager.beginTransaction().replace(
                holder.containerYouTubePlayer.id,
                youTubePlayerFragment
            ).commit()

            youTubePlayerFragment.initialize(
                DeveloperKey.DEVELOPER_KEY,
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        arg0: YouTubePlayer.Provider?,
                        player: YouTubePlayer,
                        wasRestored: Boolean
                    ) {
                        if (!wasRestored) {
                            player.cueVideo(videoID[position])
                            // player.setFullscreen(true)
                            // player.loadVideo("2zNSgSzhBfM")
                            // player.play()
                        }
                    }

                    override fun onInitializationFailure(
                        arg0: YouTubePlayer.Provider?,
                        arg1: YouTubeInitializationResult?
                    ) {
                        // TODO
                    }
                })
        }

    }

    override fun getItemCount(): Int {
        return videoID.size
    }

    class VideoInfoHolder(itemView: View) : ViewHolder(itemView) {
        var containerYouTubePlayer: FrameLayout =
            itemView.findViewById(R.id.youtube_holder) as FrameLayout
        var relativeLayoutOverYouTubeThumbnailView: RelativeLayout =
            itemView.findViewById<View>(R.id.relativeLayout_over_youtube_thumbnail) as RelativeLayout
        var youTubeThumbnailView: YouTubeThumbnailView =
            itemView.findViewById<View>(R.id.youtube_thumbnail) as YouTubeThumbnailView
        var playButton: ImageView = itemView.findViewById<View>(R.id.btnYoutube_player) as ImageView
    }

}
