package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zexceed.aniflix.databinding.ItemScheduleAnimeBinding
import com.zexceed.aniflix.models.remote.response.schedule.Anime
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.ui.animedetail.AnimeDetailActivity
import com.zexceed.aniflix.ui.home.schedule.ScheduleViewModel
import com.zexceed.aniflix.utils.Constants.createImageProgress

class ScheduleAnimeAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: ScheduleViewModel
): ListAdapter<Anime, ScheduleAnimeAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScheduleAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemScheduleAnimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Anime) {
            binding.apply {
                tvTitle.text = data.anime_name

                viewModel.getAnime(data.id)
                viewModel.schedule_anime.observe(viewLifecycleOwner) { result ->
                    when(result) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            Glide.with(itemView.context)
                                .load(result.data.thumb)
                                .placeholder(itemView.context.createImageProgress())
                                .error(android.R.color.darker_gray)
                                .into(imgThumbnail)
                        }
                        is Resource.Error -> {

                        }
                    }
                }

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