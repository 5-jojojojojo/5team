package com.android.youtubeproject.fragment.myvideofragment

import com.android.youtubeproject.api.serverdata.VideoSnippet
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.LayoutMyvideoItemBinding
import com.bumptech.glide.Glide

class MyVideoFragmentAdapter(var mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface ItemClick {
        fun onClick(position: Int)
        fun onLongClick(position: Int)

    }
    var itemClick: ItemClick? = null
    private val items = mutableListOf<YoutubeModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val binding = LayoutMyvideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        val item = items[position].snippet
//        (holder as ItemViewHolder).bind(item)

        Glide.with(mContext)
            .load(items[position].url)
            .into((holder as ItemViewHolder).iv_image)

        holder.tv_title.text = items[position].title
        holder.tv_channelId.text = items[position].channelname
        //항목 클릭 이벤트
        holder.cv_item.setOnClickListener {
            itemClick?.onClick(position)
        }
        holder.cv_item.setOnLongClickListener {
            itemClick?.onLongClick(position)
            true
        }
    }

    fun addItems(datas: MutableList<YoutubeModel>, isClear: Boolean) {
        if (isClear) items.clear()

        items.addAll(datas)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: LayoutMyvideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        fun bind(item: VideoSnippet) {
//            binding.item = item
//        }

        var iv_image: ImageView = binding.ivImage
        var tv_title: TextView = binding.tvTitle
        var tv_channelId: TextView = binding.tvChannelId
        var cv_item: CardView = binding.cvItem

    }
}