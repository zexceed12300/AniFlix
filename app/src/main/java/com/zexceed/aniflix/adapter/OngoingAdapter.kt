package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.aniflix.databinding.ItemOngoingVerticalBinding
import com.zexceed.aniflix.models.remote.response.ongoing.Anime
import com.zexceed.aniflix.ui.animedetail.AnimeDetailActivity
import com.zexceed.aniflix.utils.Constants.createImageProgress

class OngoingAdapter: ListAdapter<Anime, OngoingAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOngoingVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemOngoingVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Anime) {
            binding.apply {
                tvDayUpdated.text = data.day_updated
                tvTitle.text = data.title
                tvUploadedOn.text = data.uploaded_on
                Glide.with(itemView.context)
                    .load(data.thumb)
                    .placeholder(itemView.context.createImageProgress())
                    .error(android.R.color.darker_gray)
                    .into(imgThumbnail)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, AnimeDetailActivity::class.java)
                    intent.putExtra(AnimeDetailActivity.ANIME_DETAIL_ID, data.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Anime> = object: DiffUtil.ItemCallback<Anime>() {
            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem == newItem
            }

        }
    }
}