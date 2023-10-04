//package com.android.youtubeproject.api.serverdata
//data class CommentThreadListResponse(
//    val kind: String,
//    val etag: String,
//    val nextPageToken: String,
//    val pageInfo: PageInfo2,
//    val items: List<CommentThreadItem>
//)
//
//data class PageInfo2(
//    val totalResults: Int,
//    val resultsPerPage: Int
//)
//
//data class CommentThreadItem(
//    val kind: String,
//    val etag: String,
//    val id: String,
//    val snippet: SnippetComment
//)
//
//data class SnippetComment(
//    val channelId: String,
//    val videoId: String,
//    val topLevelComment: TopLevelComment,
//    val canReply: Boolean,
//    val totalReplyCount: Int,
//    val isPublic: Boolean
//)
//
//data class TopLevelComment(
//    val kind: String,
//    val etag: String,
//    val id: String,
//    val snippet: CommentSnippet
//)
//
//data class CommentSnippet(
//    val channelId: String,
//    val videoId: String,
//    val textDisplay: String,
//    val textOriginal: String,
//    val authorDisplayName: String,
//    val authorProfileImageUrl: String,
//    val authorChannelUrl: String,
//    val authorChannelId: AuthorChannelId,
//    val canRate: Boolean,
//    val viewerRating: String,
//    val likeCount: Int,
//    val publishedAt: String,
//    val updatedAt: String
//)
//
//data class AuthorChannelId(
//    val value: String
//)