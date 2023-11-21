package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.aniflix.databinding.ItemHistoryBinding
import com.zexceed.aniflix.models.local.room.HistoryEntity
import com.zexceed.aniflix.ui.animedetail.AnimeDetailActivity
import com.zexceed.aniflix.utils.Constants.createImageProgress
import com.zexceed.aniflix.utils.Constants.formatLong

class HistoryAdapter: ListAdapter<HistoryEntity, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryEntity) {
            binding.apply {
                tvTitle.text = data.title
                tvEpisode.text = "Episode ${data.episode}"
                tvTimestamp.text =  data.timestamp.formatLong("HH:mm")
                Glide.with(itemView.context)
                    .load(data.thumb)
                    .placeholder(itemView.context.createImageProgress())
                    .error(android.R.color.darker_gray)
                    .into(imgThumbnail)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, AnimeDetailActivity::class.java)
                    intent.putExtra(AnimeDetailActivity.ANIME_DETAIL_ID, data.animeId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HistoryEntity> = object: DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem.animeId == newItem.animeId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}