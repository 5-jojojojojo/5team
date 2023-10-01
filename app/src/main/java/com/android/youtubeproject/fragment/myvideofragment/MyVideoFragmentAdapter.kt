package com.android.youtubeproject.fragment.myvideofragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.api.model.YoutubeModel
import com.android.youtubeproject.databinding.LayoutMyvideoItemBinding

class MyVideoFragmentAdapter(var mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface ItemClick {
        fun onClick(position: Int)
        fun onLongClick(position: Int)

    }
    interface DataChangeListener {
        fun onDataChanged(isEmpty: Boolean)
    }
    var itemClick: ItemClick? = null
    var dataChangeListener: DataChangeListener? = null

    private val items = mutableListOf<YoutubeModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val binding = LayoutMyvideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = items[position]
        (holder as ItemViewHolder).bind(item)

        //항목 클릭 이벤트
        holder.binding.cvItem.setOnClickListener {
            itemClick?.onClick(position)
        }
        holder.binding.cvItem.setOnLongClickListener {
            itemClick?.onLongClick(position)
            true
        }
    }

    fun addItems(datas: MutableList<YoutubeModel>, isClear: Boolean) {
        if (isClear) items.clear()

        items.addAll(datas)
        notifyDataSetChanged()

        dataChangeListener?.onDataChanged(items.isEmpty())
    }

    inner class ItemViewHolder(val binding: LayoutMyvideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: YoutubeModel) {
            binding.item = item
        }

    }
}