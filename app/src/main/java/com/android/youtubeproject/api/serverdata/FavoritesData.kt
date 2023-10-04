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
    val snippet: VideoSnippet,
    val contentDetails: VideoContentDetails,
    val statistics: VideoStatistics,
    val player: Player
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
data class VideoContentDetails(
    val duration: String,
    val dimension: String,
    val definition: String,
    val caption: Boolean,
    val licensedContent: Boolean,
    val contentRating: ContentRating,
    val projection: String
)
data class ContentRating(
    val acbRating: String,
    val agcomRating: String,
    val anatelRating: String,
    val bbfcRating: String,
    val bfvcRating: String,
    val bmukkRating: String,
    val catvRating: String,
    val catvfrRating: String,
    val cbfcRating: String,
    val cccRating: String,
    val cceRating: String,
    val chfilmRating: String,
    val chvrsRating: String,
    val cicfRating: String,
    val cnaRating: String,
    val cncRating: String,
    val csaRating: String,
    val cscfRating: String,
    val czfilmRating: String,
    val djctqRating: String,
    val djctqRatingReasons: List<String>,
    val ecbmctRating: String,
    val eefilmRating: String,
    val egfilmRating: String,
    val eirinRating: String,
    val fcbmRating: String,
    val fcoRating: String,
    val fmocRating: String,
    val fpbRating: String,
    val fpbRatingReasons: List<String>,
    val fskRating: String,
    val grfilmRating: String,
    val icaaRating: String,
    val ifcoRating: String,
    val ilfilmRating: String,
    val incaaRating: String,
    val kfcbRating: String,
    val kijkwijzerRating: String,
    val kmrbRating: String,
    val lsfRating: String,
    val mccaaRating: String,
    val mccypRating: String,
    val mcstRating: String,
    val mdaRating: String,
    val medietilsynetRating: String,
    val mekuRating: String,
    val mibacRating: String,
    val mocRating: String,
    val moctwRating: String,
    val mpaaRating: String,
    val mpaatRating: String,
    val mtrcbRating: String,
    val nbcRating: String,
    val nbcplRating: String,
    val nfrcRating: String,
    val nfvcbRating: String,
    val nkclvRating: String,
    val oflcRating: String,
    val pefilmRating: String,
    val rcnofRating: String,
    val resorteviolenciaRating: String,
    val rtcRating: String,
    val rteRating: String,
    val russiaRating: String,
    val skfilmRating: String,
    val smaisRating: String,
    val smsaRating: String,
    val tvpgRating: String,
    val ytRating: String
)
data class VideoStatistics(
    val viewCount: String,
    val likeCount: String,
    val favoriteCount: String,
    val commentCount: String
)
data class Player(
    val embedHtml: String,
    val embedHeight: Long,
    val embedWidth: Long
)