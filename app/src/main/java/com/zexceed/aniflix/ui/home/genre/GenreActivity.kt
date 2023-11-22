package com.zexceed.aniflix.ui.home.genre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.zexceed.aniflix.adapter.AnimeGenreAdapter
import com.zexceed.aniflix.databinding.ActivityGenreBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.utils.ViewModelFactory

class GenreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenreBinding
    private lateinit var viewModel: GenreViewModel
    private lateinit var mAdapter: AnimeGenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@GenreActivity)

        mAdapter = AnimeGenreAdapter()

        binding.apply {
            imgBack.setOnClickListener {
                finish()
            }

            tvGenreTitle.text = intent.getStringExtra(GENRE_TITLE)

            viewModel.getAnimeByGenre(intent.getStringExtra(GENRE_ID).toString(), 1)
            viewModel.anime.observe(this@GenreActivity) { result ->
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        mAdapter.submitList(result.data.animeList)
                        rvGenres.apply {
                            adapter = mAdapter
                            setHasFixedSize(true)
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun obtainViewModel(activity: GenreActivity): GenreViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[GenreViewModel::class.java]
    }

    companion object {
        const val GENRE_TITLE = "genreTitle"
        const val GENRE_ID = "genreId"
    }
}