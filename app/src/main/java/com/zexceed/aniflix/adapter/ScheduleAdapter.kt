package com.zexceed.aniflix.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zexceed.aniflix.databinding.ItemScheduleBinding
import com.zexceed.aniflix.models.remote.response.schedule.Schedule
import com.zexceed.aniflix.ui.home.schedule.ScheduleViewModel

class ScheduleAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: ScheduleViewModel
): ListAdapter<Schedule, ScheduleAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Schedule) {
            binding.apply {
                tvDay.text = data.day
                val animeAdapter = ScheduleAnimeAdapter(viewLifecycleOwner, viewModel)
                animeAdapter.submitList(data.animeList)
                rvAnime.apply {
                    adapter = animeAdapter
                    setHasFixedSize(true)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Schedule> = object: DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.day == oldItem.day
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }

        }
    }
}