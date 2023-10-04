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
    var viewcount: String,
    var likecount: String?,
    var favoriteCount: String,
    var commentcount: String,
    var definition : String,
    var videolength: String,
    var videoPublishedDatetime: String,
    var channeltitle: String,
    var videowidth: Int,
    var videoheight: Int,
    var like:Boolean = false,
    var dislike:Boolean = false
)