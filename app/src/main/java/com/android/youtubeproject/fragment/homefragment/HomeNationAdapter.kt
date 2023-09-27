package com.android.youtubeproject.fragment.homefragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.FavoritesitemsBinding
import com.android.youtubeproject.`interface`.ItemClick
import com.bumptech.glide.Glide

class HomeNationAdapter(private val context: Context):RecyclerView.Adapter<RecyclerView
.ViewHolder>() {
    var items = ArrayList<YoutubeModel>()
    var itemClick: ItemClick? = null

    inner class nationViewHolder(private val binding:FavoritesitemsBinding):RecyclerView.ViewHolder
        (binding.root){
            fun bind(item:YoutubeModel){
                binding.apply {
                    Log.d("YouTubeProjects", "어댑터 데이터 : ${items}")
                    homeTitle.text = item.title
                    Glide.with(context)
                        .load(item.url)
                        .into(homeUrl)
                    itemView.setOnClickListener{
                        itemClick?.onClick(it, position)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FavoritesitemsBinding.inflate(LayoutInflater.from(parent.context),parent,
            false)
        return nationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as nationViewHolder
        val item = items[position]
        holder.bind(item)
    }
}