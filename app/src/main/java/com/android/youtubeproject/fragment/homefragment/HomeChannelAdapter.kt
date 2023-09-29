package com.android.youtubeproject.fragment.homefragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.databinding.EmptyViewBinding
import com.android.youtubeproject.databinding.FavoritesitemsBinding
import com.bumptech.glide.Glide

class HomeChannelAdapter(private val context:Context):RecyclerView.Adapter<RecyclerView
.ViewHolder>() {
    var items = ArrayList<ChannelModel>()
    inner class ChannelViewHolder(private val binding:FavoritesitemsBinding):RecyclerView
    .ViewHolder(binding.root){
        fun bind(items : ChannelModel){
            binding.apply {
                homeTitle.text = items.title
                Glide.with(context)
                    .load(items.url)
                    .into(homeUrl)
            }
        }

    }
    inner class emptyViewHolder(private val binding: EmptyViewBinding):RecyclerView.ViewHolder
        (binding.root){
        fun bind(){
            binding.emptyTextView.text = "결과가 없습니다."
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == Constants.EMPTY_TYPE){
            val binding = EmptyViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            emptyViewHolder(binding)
        }else{
            val binding = FavoritesitemsBinding.inflate(LayoutInflater.from(parent.context),parent,
                false)
            ChannelViewHolder(binding)
        }
    }
    override fun getItemCount(): Int {
        return if (items.isEmpty()) 1 else items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == Constants.CHANNEL_TYPE){
            holder as HomeChannelAdapter.ChannelViewHolder
            val item = items[position]
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(items.isEmpty()) Constants.EMPTY_TYPE else Constants.CHANNEL_TYPE
    }
}