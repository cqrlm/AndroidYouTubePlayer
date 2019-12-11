package com.example.lab6_youtube_app.recyclerview

import com.google.gson.annotations.SerializedName

data class YoutubeVideo(val id: String)

data class YoutubeVideoResponse(
    @SerializedName("items") val item: List<Item>
)

data class Snippet(
    @SerializedName("title")
    val title: String
)

data class Item(
    @SerializedName("snippet")
    val snippet: Snippet,
    @SerializedName("id")
    val id: String
)