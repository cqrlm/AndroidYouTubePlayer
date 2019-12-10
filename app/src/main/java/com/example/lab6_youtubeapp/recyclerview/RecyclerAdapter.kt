package com.example.lab6_youtubeapp.recyclerview

import android.content.Context
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
import com.example.lab6_youtubeapp.DeveloperKey
import com.example.lab6_youtubeapp.R
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
        val playButton: ImageView = itemView.findViewById<View>(R.id.btnYoutube_player) as ImageView
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
                    youTubeThumbnailLoader.setVideo(videos[position].id)
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener)
                }

                override fun onInitializationFailure(
                    youTubeThumbnailView: YouTubeThumbnailView?,
                    youTubeInitializationResult: YouTubeInitializationResult?
                ) {
                    // TODO
                }
            })
        holder.playButton.setOnClickListener {
            holder.containerYouTubePlayer.id = position + 1

            if (!this::youTubePlayerFragment.isInitialized) {
                youTubePlayerFragment =
                    YouTubePlayerSupportFragment.newInstance()
            }
            if ((youTubePlayerFragment as Fragment).isAdded) {
//                if (youTubePlayer != null) {
                youTubePlayer.pause()
                youTubePlayer.release()
//                    youTubePlayer = null
//                }
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

            youTubePlayerFragment.initialize(
                DeveloperKey.DEVELOPER_KEY,
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        arg0: YouTubePlayer.Provider?,
                        player: YouTubePlayer,
                        wasRestored: Boolean
                    ) {
                        if (!wasRestored) {
                            youTubePlayer = player
                            youTubePlayer.cueVideo(videos[position].id)
//                            player.cuePlaylist("PLWz5rJ2EKKc8jQfNAUu5reIGFNNqpn26X")
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
        return videos.size
    }
}
