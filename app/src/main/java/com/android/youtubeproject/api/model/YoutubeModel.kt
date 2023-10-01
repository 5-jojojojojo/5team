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
    var like:Boolean,
    var dislike:Boolean
)