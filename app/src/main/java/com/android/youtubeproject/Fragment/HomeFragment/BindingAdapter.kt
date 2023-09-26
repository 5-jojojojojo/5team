package com.android.youtubeproject.Fragment.HomeFragment

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgRes")
fun imgRes(imageView: ImageView, url:String) {
    // Glide
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

//@BindingAdapter("imgRes")
//fun imgRes(imageView: ImageView, item: VideoSnippet) {
//    // Glide
//    Glide.with(imageView.context)
//        .load(item.thumbnails.default.url)
//        .into(imageView)
//}
//
//@BindingAdapter("setTextChannelId")
//fun setTextChannelId(textView: TextView, item: VideoSnippet) {
//    textView.text = item.channelId
//}