package com.android.youtubeproject.fragment.videodetailfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.youtubeproject.api.model.CommentModel
import com.android.youtubeproject.databinding.CommentitemBinding
import com.bumptech.glide.Glide

class VideoDetailCommentAdapter(private val comments: ArrayList<CommentModel>) : RecyclerView.Adapter<VideoDetailCommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: CommentitemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentModel) {
             Glide.with(binding.root.context).
             load(comment.authorProfileImageUrl).
             into(binding.authorImage)

            binding.authorName.text = comment.authorDisplayName
            binding.commentText.text = comment.textOriginal
            binding.commentDate.text = comment.publishedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommentitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }
}