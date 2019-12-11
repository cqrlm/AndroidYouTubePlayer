package com.example.lab6_youtube_app.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lab6_youtube_app.DeveloperKey
import com.example.lab6_youtube_app.R
import com.google.android.youtube.player.*

class RecyclerAdapter(ctx: Context, val videos: List<VideoItem>) :
    RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder>() {

    inner class VideoInfoHolder(itemView: View) : ViewHolder(itemView) {
        val containerYouTubePlayer: FrameLayout =
            itemView.findViewById(R.id.youtube_holder) as FrameLayout
        val relativeLayoutOverYouTubeThumbnailView: RelativeLayout =
            itemView.findViewById<View>(R.id.relativeLayout_over_youtube_thumbnail) as RelativeLayout
        val youTubeThumbnailView: YouTubeThumbnailView =
            itemView.findViewById<View>(R.id.youtube_thumbnail) as YouTubeThumbnailView
        val playButton: ImageView =
            itemView.findViewById<View>(R.id.btn_youtube_player) as ImageView
    }

    lateinit var youTubePlayer: YouTubePlayer
    private lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment
    private val fragmentManager = (ctx as AppCompatActivity).supportFragmentManager

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
                    Log.d("ThumbnailError", errorReason as String)
                }

                override fun onThumbnailLoaded(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    strng: String?
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
                    youTubeThumbnailLoader.setVideo(videos[position].id)
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener)
                }

                override fun onInitializationFailure(
                    youTubeThumbnailView: YouTubeThumbnailView?,
                    youTubeInitializationResult: YouTubeInitializationResult?
                ) {
                    Log.d("InitializationFailure", youTubeInitializationResult as String)
                }
            })
        holder.playButton.setOnClickListener {
            holder.containerYouTubePlayer.id = position + 1

            if (!this::youTubePlayerFragment.isInitialized) {
                youTubePlayerFragment =
                    YouTubePlayerSupportFragment.newInstance()
            }
            if ((youTubePlayerFragment as Fragment).isAdded) {
                youTubePlayer.pause()
                youTubePlayer.release()

                fragmentManager
                    .beginTransaction()
                    .remove(youTubePlayerFragment as Fragment)
                    .commit()
                fragmentManager
                    .executePendingTransactions()
                youTubePlayerFragment.onDestroy()
            }
            if (!this::youTubePlayerFragment.isInitialized) {
                youTubePlayerFragment =
                    YouTubePlayerSupportFragment.newInstance()
            }
            fragmentManager
                .beginTransaction()
                .add(
                    holder.containerYouTubePlayer.id,
                    youTubePlayerFragment as Fragment
                )
                .commit()

            var check = false
            youTubePlayerFragment.initialize(
                DeveloperKey.DEVELOPER_KEY,
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        provider: YouTubePlayer.Provider?,
                        player: YouTubePlayer,
                        wasRestored: Boolean
                    ) {
                        if (!wasRestored) {
                            youTubePlayer = player
                            youTubePlayer.setOnFullscreenListener {
                                if (!check) {
                                    check = true
                                    youTubePlayer.setFullscreen(check)
                                    youTubePlayer.cueVideo(videos[position].id)
                                }
                            }
                            youTubePlayer.fullscreenControlFlags = 0
                            youTubePlayer.cueVideo(videos[position].id)
//                            player.cuePlaylist("PLWz5rJ2EKKc8jQfNAUu5reIGFNNqpn26X")
                        }
                    }

                    override fun onInitializationFailure(
                        provider: YouTubePlayer.Provider?,
                        youTubeInitializationResult: YouTubeInitializationResult?
                    ) {
                        Log.d("InitializationFailure", youTubeInitializationResult as String)
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return videos.size
    }
}
