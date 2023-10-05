package com.android.youtubeproject.api.model

data class CommentModel(
    val textOriginal: String,
    val authorDisplayName: String,
    val authorProfileImageUrl: String,
    val likeCount: Int,
    val publishedAt: String,
    var nextpagetoken: String

)