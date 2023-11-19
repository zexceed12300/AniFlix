package com.zexceed.aniflix.ui.animedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zexceed.aniflix.R
import com.zexceed.aniflix.adapter.EpisodeAdapter
import com.zexceed.aniflix.adapter.GenreAdapter
import com.zexceed.aniflix.databinding.ActivityAnimeDetailBinding
import com.zexceed.aniflix.models.remote.response.Episode
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.ui.home.complete.CompleteViewModel
import com.zexceed.aniflix.utils.Constants
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.ViewModelFactory
import java.util.Collections

class AnimeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimeDetailBinding
    private lateinit var viewModel: AnimeDetailViewModel
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@AnimeDetailActivity)

        genreAdapter = GenreAdapter()

        binding.apply {

            episodeAdapter = EpisodeAdapter(
                onClick = { data ->
                    tvTitle.text = data.title
                    viewModel.getEpisode(data.id)
                    setEpisodeView()
                }
            )

            viewModel.getAnime(intent.getStringExtra(ANIME_DETAIL_ID).toString())
            setAnimeView()
        }
    }

    private fun setAnimeView() {
        binding.apply {
            viewModel.anime.observe(this@AnimeDetailActivity) { result ->
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        tvStatus.text = result.data.status
                        if (result.data.score.toString() == "null") {
                            tvRating.text = "N/A"
                        } else {
                            tvRating.text = result.data.score.toString()
                        }

                        val episode = result.data.episode_list
                        Collections.reverse(episode)
                        tvTitle.text = episode[0].title

                        viewModel.getEpisode(episode[0].id)
                        setEpisodeView()

                        episodeAdapter.submitList(episode)
                        rvEpisode.apply {
                            adapter = episodeAdapter
                            setHasFixedSize(true)
                        }

                        genreAdapter.submitList(result.data.genre_list)
                        Log.d(TAG, "setView: ${result.data.genre_list}")
                        rvGenre.apply {
                            adapter = genreAdapter
                            setHasFixedSize(true)
                        }
                        tvSynopsis.text = result.data.synopsis
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun setEpisodeView() {
        binding.apply {
            viewModel.episode.observe(this@AnimeDetailActivity) { result ->
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        videoContainer.webViewClient = WebViewClient()
                        videoContainer.settings.javaScriptEnabled = true
                        videoContainer.loadUrl(result.data.link_stream)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun obtainViewModel(activity: AnimeDetailActivity): AnimeDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AnimeDetailViewModel::class.java]
    }

    companion object {
        const val ANIME_DETAIL_ID = "animeDetailId"
    }

}