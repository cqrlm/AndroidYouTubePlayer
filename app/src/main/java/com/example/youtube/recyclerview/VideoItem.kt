package com.example.youtube.recyclerview

import com.google.gson.annotations.SerializedName

data class YoutubeVideo(val id: String)

data class YoutubeVideoResponse(@SerializedName("items") val items: List<Item>)

data class Item(@SerializedName("id") val id : String,
                @SerializedName("snippet") val snippet : Snippet)

data class Snippet(@SerializedName("title") val title: String)