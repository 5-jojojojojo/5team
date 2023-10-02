package com.android.youtubeproject.api.serverdata

data class SearchData(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val regionCode: String,
    val pageInfo: SearchPageInfo,
    val items: List<YouTubeSearchResult>
)

data class SearchPageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class YouTubeSearchResult(
    val kind: String,
    val etag: String,
    val id: YouTubeVideoId,
    val snippet: YouTubeSnippet
)

data class YouTubeVideoId(
    val kind: String,
    val videoId: String
)

data class YouTubeSnippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: YouTubeThumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: String
)

data class YouTubeThumbnails(
    val default: YouTubeThumbnail,
    val medium: YouTubeThumbnail,
    val high: YouTubeThumbnail
)

data class YouTubeThumbnail(
    val url: String,
    val width: Int,
    val height: Int
)