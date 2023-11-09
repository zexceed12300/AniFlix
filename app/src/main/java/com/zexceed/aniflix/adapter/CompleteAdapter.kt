package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.aniflix.databinding.ItemMainCompleteBinding
import com.zexceed.aniflix.models.remote.response.home.Complete
import com.zexceed.aniflix.utils.Constants.createImageProgress

class CompleteAdapter: ListAdapter<Complete, CompleteAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainCompleteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMainCompleteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Complete) {
            binding.apply {
                tvRating.text = data.score.toString()
                tvTitle.text = data.title
                tvEpisode.text = data.episode
                Glide.with(itemView.context)
                    .load(data.thumb)
                    .placeholder(itemView.context.createImageProgress())
                    .error(android.R.color.darker_gray)
                    .into(imgThumbnail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Complete> = object: DiffUtil.ItemCallback<Complete>() {
            override fun areItemsTheSame(oldItem: Complete, newItem: Complete): Boolean {
                return newItem.id == oldItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Complete, newItem: Complete): Boolean {
                return oldItem == newItem
            }

        }
    }
}