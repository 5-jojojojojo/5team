package com.android.youtubeproject.api.model

class DetailChannelModel(
    var title: String,
    var description: String,
    var publishedAt: String,
    var viewCount: Long,
    var subscriberCount: Long,
    var hiddenSubscriberCount: Boolean,
    var videoCount: Long,
    var thumbnailsurl: String,
    var thumbnailswidth: Int,
    var thumbnailsheight: Int
)