package com.android.youtubeproject.fragment.myvideofragment

import com.android.youtubeproject.api.serverdata.VideoSnippet
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.databinding.LayoutMyvideoItemBinding
import com.bumptech.glide.Glide

class MyVideoFragmentAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface ItemClick {
        fun onClick(position: Int)
        fun onLongClick(position: Int)

    }
    var itemClick: ItemClick? = null
    private val items = mutableListOf<VideoSnippet>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutMyvideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mContext)
            .load(items[position].thumbnails)
            .into((holder as ItemViewHolder).iv_image)

        holder.tv_title.text = items[position].title
        holder.tv_channelId.text = items[position].channelId
//항목 클릭 이벤트
        holder.cv_item.setOnClickListener{
            itemClick?.onClick(position)
        }
        holder.cv_item.setOnLongClickListener{
            itemClick?.onLongClick(position)
            true
        }
    }


    inner class ItemViewHolder(binding: LayoutMyvideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var iv_image: ImageView = binding.ivImage
        var tv_title: TextView = binding.tvTitle
        var tv_channelId: TextView = binding.tvChannelId
        var cv_item: CardView = binding.cvItem

    }
}
//fun addItems(resData: List<ChannelData>, isClear: Boolean) {
//    if(isClear) items.clear()
//
//    items.addAll(resData)
//    notifyDataSetChanged()
//}