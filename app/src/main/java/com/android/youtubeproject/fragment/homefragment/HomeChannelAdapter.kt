package com.android.youtubeproject.fragment.homefragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.api.model.ChannelModel
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FavoritesitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChannelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ChannelViewHolder
        val items = items[position]
        holder.bind(items)
    }
}