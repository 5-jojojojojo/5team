package com.android.youtubeproject.API

data class ChannelData(
    val kind: String,
    val etag: String,
    val items: ArrayList<ChannelItems>,
    val pageInfo: PageInfo
)

data class ChannelItems(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: VideoSnippet
)

data class VideoSnippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Map<String, VideoThumbnail>,
    val channelTitle: String,
    val tags: ArrayList<String>,
    val categoryId: String,
    val liveBroadcastContent: String,
    val localized: VideoLocalization,
    val defaultAudioLanguage: String
)

data class VideoThumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

data class VideoLocalization(
    val title: String,
    val description: String
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)