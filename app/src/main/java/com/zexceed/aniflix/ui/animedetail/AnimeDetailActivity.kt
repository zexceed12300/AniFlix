package com.zexceed.aniflix.ui.animedetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.zexceed.aniflix.adapter.EpisodeAdapter
import com.zexceed.aniflix.adapter.GenreAdapter
import com.zexceed.aniflix.databinding.ActivityAnimeDetailBinding
import com.zexceed.aniflix.models.local.room.HistoryEntity
import com.zexceed.aniflix.models.local.room.MylistEntity
import com.zexceed.aniflix.models.remote.response.Episode
import com.zexceed.aniflix.models.remote.response.anime.AnimeResponse
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.ui.home.genre.GenreActivity
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.ViewModelFactory
import kotlinx.coroutines.launch
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

        genreAdapter = GenreAdapter(
            onClick = { data ->
                val intent = Intent(this@AnimeDetailActivity, GenreActivity::class.java)
                intent.putExtra(GenreActivity.GENRE_TITLE, data.genre_title)
                intent.putExtra(GenreActivity.GENRE_ID, data.genre_id)
                startActivity(intent)
            }
        )

        binding.apply {

            episodeAdapter = EpisodeAdapter(
                onClick = { data, position ->
                    tvTitle.text = data.title

                    viewModel.getEpisode(data.id)
                    setMediaPlayer()
                    setHistory(
                        intent.getStringExtra(ANIME_DETAIL_ID).toString(),
                        null,
                        null,
                        position+1
                    )
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

                        val listEpisode = result.data.episode_list.asReversed()
                        var currentEps = listEpisode[0]
                        if (viewModel.episodeId.value.isNullOrEmpty()) {
                            viewModel.getEpisode(listEpisode[0].id)
                        } else {
                            currentEps = listEpisode.find {
                                it.id == viewModel.episodeId.value
                            } ?: listEpisode[0]
                        }

                        tvTitle.text = currentEps?.title

                        setEpisodeList(listEpisode)

                        setMediaPlayer()

                        setHistory(
                            result.data.anime_id,
                            result.data.title,
                            result.data.thumb,
                            1,
                        )

                        setGenreList(result)
                        if (result.data.synopsis.isNullOrEmpty()) {
                            chipSynopsisError?.visibility = View.VISIBLE
                        } else {
                            tvSynopsis.text = result.data.synopsis
                        }

                        setMyList(result)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun setHistory(animeId: String, title: String?, thumb: String?, episode: Int) {
        viewModel.getHistoryById(animeId).observe(this@AnimeDetailActivity) { result ->
            lifecycleScope.launch {
                if (result != null && title.isNullOrEmpty() && thumb.isNullOrEmpty()) {
                    viewModel.insertHistory(
                        history = HistoryEntity(
                            animeId = result.animeId,
                            title = result.title,
                            thumb = result.thumb,
                            episode = episode,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                } else if (!title.isNullOrEmpty() && !thumb.isNullOrEmpty()) {
                    viewModel.insertHistory(
                        history = HistoryEntity(
                            animeId = animeId,
                            title = title,
                            thumb = thumb,
                            episode = episode,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            }
        }
    }

    private fun setMyList(result: Resource.Success<AnimeResponse>) {
        binding.apply {
            checkMylist.setOnCheckedChangeListener { checkbox, isChecked ->
                if (isChecked) {
                    lifecycleScope.launch {
                        viewModel.storeMylist(
                            anime = MylistEntity(
                                animeId = result.data.anime_id,
                                title = result.data.title,
                                thumb = result.data.thumb
                            )
                        )
                    }
                } else {
                    lifecycleScope.launch {
                        viewModel.deleteMylist(result.data.anime_id)
                    }
                }
            }
            viewModel.getMylist(result.data.anime_id).observe(this@AnimeDetailActivity) { result ->
                Log.d(TAG, "setMyList: $result")
                if (result != null) {
                    checkMylist.isChecked = true
                }
            }
        }
    }

    private fun setEpisodeList(list: List<Episode>) {
        binding.apply {
            episodeAdapter.submitList(list)
            rvEpisode.apply {
                adapter = episodeAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun setGenreList(result: Resource.Success<AnimeResponse>) {
        binding.apply {
            genreAdapter.submitList(result.data.genre_list)
            Log.d(TAG, "setView: ${result.data.genre_list}")
            rvGenre.apply {
                adapter = genreAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun setMediaPlayer() {
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