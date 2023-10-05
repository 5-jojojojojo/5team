package com.android.youtubeproject.fragment.searchfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.Constants
import com.android.youtubeproject.api.model.ChannelModel
import com.android.youtubeproject.databinding.EmptyViewBinding
import com.android.youtubeproject.databinding.FavoritesitemsBinding
import com.android.youtubeproject.`interface`.ItemClick
import com.bumptech.glide.Glide

class SearchFragmentAdapter(private val context:Context):RecyclerView.Adapter<RecyclerView
.ViewHolder>() {
    var items = ArrayList<ChannelModel>()
    var itemClick: ItemClick? = null
    inner class SearchViewHolder(private val binding:FavoritesitemsBinding) : RecyclerView
    .ViewHolder(binding.root){
        fun bind(items:ChannelModel){
            binding.apply {
                homeTitle.text = items.title
                Glide.with(context)
                    .load(items.url)
                    .into(homeUrl)
                itemView.setOnClickListener{
                    itemClick?.onClick(it, position)
                }
            }
        }
    }
    inner class emptyViewHolder(private val binding: EmptyViewBinding):RecyclerView.ViewHolder
        (binding.root){
        fun bind(){
            binding.emptyTextView.text = "검색 결과가 없습니다."
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == Constants.EMPTY_TYPE){
            val binding = EmptyViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            emptyViewHolder(binding)
        }else{
            val binding = FavoritesitemsBinding.inflate(LayoutInflater.from(parent.context),
                parent,false)
            SearchViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 1 else items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == Constants.SEARCH_TYPE){
            holder as SearchViewHolder
            val items = items[position]
            holder.bind(items)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if(items.isEmpty()) Constants.EMPTY_TYPE else Constants.SEARCH_TYPE
    }
}