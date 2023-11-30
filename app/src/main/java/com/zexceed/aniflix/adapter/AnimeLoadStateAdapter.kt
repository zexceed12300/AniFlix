package com.zexceed.aniflix.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.LoadStates
import androidx.recyclerview.widget.RecyclerView
import com.zexceed.aniflix.databinding.AnimeLoadStateFooterBinding

class AnimeLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<AnimeLoadStateAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: AnimeLoadStateFooterBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                shimmer.isVisible = loadState is LoadState.Loading
                layoutError.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = AnimeLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

}