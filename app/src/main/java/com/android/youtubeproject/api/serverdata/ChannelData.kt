package com.android.youtubeproject.api.serverdata

data class ChannelData(
    val kind: String,
    val etag: String,
    val pageInfo: PageInfo,
    val items: ArrayList<Channel>
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class Channel(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet
)

data class Snippet(
    val title: String,
    val description: String,
    val customUrl: String,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val localized: Localized
)

data class Thumbnails(
    val default: Thumbnail,
    val medium: Thumbnail,
    val high: Thumbnail
)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

data class Localized(
    val title: String,
    val description: String
)