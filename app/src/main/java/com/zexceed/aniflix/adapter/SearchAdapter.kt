package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.aniflix.databinding.ItemSearchBinding
import com.zexceed.aniflix.models.remote.response.search.SearchResult
import com.zexceed.aniflix.ui.animedetail.AnimeDetailActivity
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.Constants.createImageProgress

class SearchAdapter: ListAdapter<SearchResult, SearchAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchResult) {
            binding.apply {

                tvTitle.text = data.title
                tvStatus.text = data.status
                if (data.score.toString() == "null") {
                    tvRating.text = "N/A"
                } else {
                    tvRating.text = data.score.toString()
                }

                val genreAdapter = GenreAdapter()
                genreAdapter.submitList(data.genre_list)
                rvGenre.apply {
                    adapter = genreAdapter
                    setHasFixedSize(true)
                }

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
        val DIFF_CALLBACK: DiffUtil.ItemCallback<SearchResult> = object: DiffUtil.ItemCallback<SearchResult>() {
            override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return newItem.id == oldItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return oldItem == newItem
            }

        }
    }
}