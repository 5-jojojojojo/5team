package com.android.youtubeproject.api.serverdata

data class DetailChannelData(
    val kind: String,
    val etag: String,
    val pageInfo: PageInfo1,
    val items: List<ChannelItem>
)

data class PageInfo1(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class ChannelItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet1,
    val statistics: Statistics
)

data class Snippet1(
    val title: String,
    val description: String,
    val customUrl: String,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val localized: Localized
)

data class Thumbnails(
    val default: ThumbnailDetail,
    val medium: ThumbnailDetail,
    val high: ThumbnailDetail
)

data class ThumbnailDetail(
    val url: String,
    val width: Int,
    val height: Int
)

data class Localized(
    val title: String,
    val description: String
)

data class Statistics(
    val viewCount: Long,
    val subscriberCount: Long,
    val hiddenSubscriberCount: Boolean,
    val videoCount: Long
)