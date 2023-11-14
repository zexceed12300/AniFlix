package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.aniflix.databinding.ItemOngoingHorizontalBinding
import com.zexceed.aniflix.models.remote.response.home.OnGoing
import com.zexceed.aniflix.utils.Constants.createImageProgress

class HomeOngoingAdapter: ListAdapter<OnGoing, HomeOngoingAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOngoingHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemOngoingHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OnGoing) {
            binding.apply {
                tvDayUpdated.text = data.day_updated
                tvTitle.text = data.title
                tvUploadedOn.text = data.uploaded_on
                Glide.with(itemView.context)
                    .load(data.thumb)
                    .placeholder(itemView.context.createImageProgress())
                    .error(android.R.color.darker_gray)
                    .into(imgThumbnail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<OnGoing> = object: DiffUtil.ItemCallback<OnGoing>() {
            override fun areItemsTheSame(oldItem: OnGoing, newItem: OnGoing): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: OnGoing, newItem: OnGoing): Boolean {
                return oldItem == newItem
            }

        }
    }
}