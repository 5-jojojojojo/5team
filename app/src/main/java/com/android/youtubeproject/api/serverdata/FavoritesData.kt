package com.android.youtubeproject.api.serverdata

data class FavoritesData(
    val kind: String,
    val etag: String,
    val items: ArrayList<VideoItems>,
)

data class VideoItems(
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
    val thumbnails: VideoThumbnails,
    val channelTitle: String,
    val tags: ArrayList<String>,
    val categoryId: String,
    val liveBroadcastContent: String,
    val localized: VideoLocalized,
    val defaultAudioLanguage: String
)

data class VideoThumbnails(
    val default: VideoThumbnail,
    val medium: VideoThumbnail,
    val high: VideoThumbnail,
    val standard: VideoThumbnail,
    val maxres: VideoThumbnail
)

data class VideoThumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

data class VideoLocalized(
    val title: String,
    val description: String
)