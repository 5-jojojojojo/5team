package com.android.youtubeproject.fragment.searchfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.databinding.SearchitemsBinding
import com.android.youtubeproject.`interface`.ItemClick
import com.bumptech.glide.Glide

class SearchFragmentAdapter(private val context:Context):RecyclerView.Adapter<RecyclerView
.ViewHolder>() {
    var items = ArrayList<ChannelModel>()
    var itemClick: ItemClick? = null

    inner class SearchViewHolder(private val binding:SearchitemsBinding) : RecyclerView
    .ViewHolder(binding.root){
        fun bind(items:ChannelModel){
            binding.apply {
                SearchName.text = items.title
                Glide.with(context)
                    .load(items.url)
                    .into(SearchProfile)
                itemView.setOnClickListener{
                    itemClick?.onClick(it, position)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = SearchitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SearchViewHolder
        val items = items[position]
        holder.bind(items)
    }
}