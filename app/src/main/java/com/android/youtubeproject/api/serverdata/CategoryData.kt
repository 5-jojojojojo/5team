package com.android.youtubeproject.api.serverdata

data class CategoryData(
    val kind: String,
    val etag: String,
    val items: ArrayList<VideoCategory>
)

data class VideoCategory(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: VideoCategorySnippet
)

data class VideoCategorySnippet(
    val title: String,
    val assignable: Boolean,
    val channelId: String
)