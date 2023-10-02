package com.android.youtubeproject.api.model

class YoutubeModel(
    val id: String,
    var type: Int,
    var title: String,
    var url: String,
    var date: String,
    var description: String,
    var channelname: String,
    var tags: List<String>?,
    var localtitle: String,
    var localdescription:String,
    var videoid:String,
    var viewCount: String,
    var likeCount: String,
    var favoriteCount: String,
    var commentCount: String,
    var definition : String,
    var like:Boolean = false,
    var dislike:Boolean = false
)