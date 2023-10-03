package com.android.youtubeproject.api.model

class YoutubeModel(
    var type: Int,
    val id: String,
    val channelId:String,
    var title: String,
    var url: String,
    var date: String,
    var description: String,
    var channelname: String,
    var tags: List<String>?,
    var localtitle: String,
    var viewCount: String,
    var likeCount: String?,
    var favoriteCount: String,
    var commentCount: String,
    var definition : String,
    var like:Boolean = false,
    var dislike:Boolean = false
)