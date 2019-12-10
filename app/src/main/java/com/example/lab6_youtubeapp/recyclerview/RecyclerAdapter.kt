package com.example.lab6_youtubeapp.recyclerview

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
import com.example.lab6_youtubeapp.DeveloperKey
import com.example.lab6_youtubeapp.R
import com.google.android.youtube.player.*


class RecyclerAdapter(ctx: Context, val videos: List<VideoItem>) :
    RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder>() {
    var youTubePlayer: YouTubePlayer? = null
    var youTubePlayerFragment: YouTubePlayerSupportFragment? = null
    private val fragmentManager = (ctx as AppCompatActivity).supportFragmentManager
//    private val youTubePlayerFragment: YouTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance()

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
            //            if (holder.containerYouTubePlayer.size == 0) {
//                youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()
//            }
            try {
                holder.containerYouTubePlayer.id = position + 1

                if (youTubePlayerFragment == null) {
                    youTubePlayerFragment =
                        YouTubePlayerSupportFragment.newInstance()
                }
                if ((youTubePlayerFragment as Fragment).isAdded) {
                    if (youTubePlayer != null) {
                        youTubePlayer?.pause()
                        youTubePlayer?.release()
                        youTubePlayer = null
                    }
                    fragmentManager
                        .beginTransaction()
                        .remove(youTubePlayerFragment as Fragment)
                        .commit()
                    fragmentManager
                        .executePendingTransactions()
                    youTubePlayerFragment = null
                }
                if (youTubePlayerFragment == null) {
                    youTubePlayerFragment =
                        YouTubePlayerSupportFragment.newInstance()
                }
                fragmentManager
                    .beginTransaction()
                    .add(
                        holder.containerYouTubePlayer.id,
                        youTubePlayerFragment as Fragment
                    )
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .commit()

//                (ctx as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
//                    holder.containerYouTubePlayer.id,
//                    youTubePlayerFragment as Fragment
//                ).addToBackStack(null).commit()

                youTubePlayerFragment!!.initialize(
                    DeveloperKey.DEVELOPER_KEY,
                    object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationSuccess(
                            arg0: YouTubePlayer.Provider?,
                            player: YouTubePlayer,
                            wasRestored: Boolean
                        ) {
                            if (!wasRestored) {
                                youTubePlayer = player
                                youTubePlayer!!.cueVideo(videos[position].id)
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
            } catch (e: Exception) {
                Log.d("error", e.message)
            }
        }
    }

    override fun getItemCount(): Int {
        return videos.size
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
