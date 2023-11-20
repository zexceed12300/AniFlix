package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.aniflix.databinding.ItemMylistBinding
import com.zexceed.aniflix.models.local.room.MylistEntity
import com.zexceed.aniflix.ui.animedetail.AnimeDetailActivity
import com.zexceed.aniflix.ui.animedetail.AnimeDetailActivity.Companion.ANIME_DETAIL_ID
import com.zexceed.aniflix.utils.Constants.createImageProgress

class MylistAdapter: ListAdapter<MylistEntity, MylistAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MylistEntity) {
            binding.apply {
                tvTitle.text = data.title

                Glide.with(itemView.context)
                    .load(data.thumb)
                    .placeholder(itemView.context.createImageProgress())
                    .error(android.R.color.darker_gray)
                    .into(imgThumbnail)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, AnimeDetailActivity::class.java)
                    intent.putExtra(ANIME_DETAIL_ID, data.animeId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MylistEntity> = object: DiffUtil.ItemCallback<MylistEntity>() {
            override fun areItemsTheSame(oldItem: MylistEntity, newItem: MylistEntity): Boolean {
                return oldItem.animeId == newItem.animeId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: MylistEntity, newItem: MylistEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}