package com.example.youtube.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.DeveloperKey
import com.example.youtube.R
import com.google.android.youtube.player.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerAdapter(ctx: Context, val youtubeVideos: List<YoutubeVideo>) :
    RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder>() {

    inner class VideoInfoHolder(itemView: View) : ViewHolder(itemView) {
        val containerYouTubePlayer: FrameLayout =
            itemView.findViewById(R.id.youtube_holder) as FrameLayout
        val youTubeFrameLayout: RelativeLayout =
            itemView.findViewById<View>(R.id.youtube_frame_layout) as RelativeLayout
        val youTubeThumbnailView: YouTubeThumbnailView =
            itemView.findViewById<View>(R.id.youtube_thumbnail) as YouTubeThumbnailView
        val playButton: ImageView =
            itemView.findViewById<View>(R.id.btn_youtube_player) as ImageView
        val videoTitle: TextView =
            itemView.findViewById(R.id.video_title) as TextView
        val progressBar: ProgressBar =
            itemView.findViewById<View>(R.id.progress_bar) as ProgressBar
    }

    private val apiService = VideoService.create()
    private lateinit var youTubePlayer: YouTubePlayer
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
        initYouTubeThumbnailView(holder, position)

        apiService.search(youtubeVideos[position].id)
            .enqueue(object : Callback<YoutubeVideoResponse> {
                override fun onFailure(
                    call: Call<YoutubeVideoResponse>,
                    t: Throwable
                ) {
                    Log.d("CallbackFailure", t.toString())
                }

                override fun onResponse(
                    call: Call<YoutubeVideoResponse>,
                    response: Response<YoutubeVideoResponse>
                ) {
                    holder.videoTitle.text = response.body()?.items?.first()?.snippet!!.title
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
            initYouTubePlayerFragment(position)
        }
    }

    override fun getItemCount(): Int {
        return youtubeVideos.size
    }

    private fun initYouTubeThumbnailView(holder: VideoInfoHolder, position: Int) {
        holder.youTubeThumbnailView.initialize(
            DeveloperKey.DEVELOPER_KEY,
            object : YouTubeThumbnailView.OnInitializedListener {
                override fun onInitializationSuccess(
                    youTubeThumbnailView: YouTubeThumbnailView?,
                    youTubeThumbnailLoader: YouTubeThumbnailLoader
                ) {
                    youTubeThumbnailLoader.setVideo(youtubeVideos[position].id)
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(object :
                        YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                        override fun onThumbnailError(
                            youTubeThumbnailView: YouTubeThumbnailView?,
                            errorReason: YouTubeThumbnailLoader.ErrorReason?
                        ) {
                            Log.d("ThumbnailError", errorReason.toString())
                        }

                        override fun onThumbnailLoaded(
                            youTubeThumbnailView: YouTubeThumbnailView,
                            strng: String?
                        ) {
                            youTubeThumbnailView.visibility = View.VISIBLE
                            holder.youTubeFrameLayout.visibility = View.VISIBLE
                            holder.playButton.visibility = View.VISIBLE
                            holder.progressBar.visibility = View.GONE
                        }
                    })
                }

                override fun onInitializationFailure(
                    youTubeThumbnailView: YouTubeThumbnailView?,
                    youTubeInitializationResult: YouTubeInitializationResult?
                ) {
                    Log.d("InitializationFailure", youTubeInitializationResult.toString())
                }
            })
    }

    private fun initYouTubePlayerFragment(position: Int) {
        var fullscreenCheck = false
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
                            if (!fullscreenCheck) {
                                fullscreenCheck = true
                                youTubePlayer.setFullscreen(fullscreenCheck)
                                youTubePlayer.cueVideo(youtubeVideos[position].id)
                            }
                        }
                        youTubePlayer.fullscreenControlFlags = 0
                        youTubePlayer.cueVideo(youtubeVideos[position].id)
                    }
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider?,
                    youTubeInitializationResult: YouTubeInitializationResult?
                ) {
                    Log.d("InitializationFailure", youTubeInitializationResult.toString())
                }
            })
    }
}
