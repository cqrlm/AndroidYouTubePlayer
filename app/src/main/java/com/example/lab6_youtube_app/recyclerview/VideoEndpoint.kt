package com.example.lab6_youtube_app.recyclerview

import com.example.lab6_youtube_app.DeveloperKey
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {
    @GET("videos?part=snippet&key=${DeveloperKey.DEVELOPER_KEY}")
    fun search(@Query("id") videoID: String): Call<YoutubeVideoResponse>

    companion object Factory {
        fun create(): VideoService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(VideoService::class.java)
        }
    }
}

