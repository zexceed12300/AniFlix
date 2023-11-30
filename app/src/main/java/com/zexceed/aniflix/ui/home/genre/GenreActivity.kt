package com.zexceed.aniflix.ui.home.genre

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zexceed.aniflix.adapter.AnimeGenreAdapter
import com.zexceed.aniflix.adapter.AnimeGenrePagingAdapter
import com.zexceed.aniflix.adapter.AnimeLoadStateAdapter
import com.zexceed.aniflix.databinding.ActivityGenreBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.ViewModelFactory
import kotlinx.coroutines.launch

class GenreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenreBinding
    private lateinit var viewModel: GenreViewModel
    private lateinit var mAdapter: AnimeGenreAdapter
    private lateinit var mPagingAdapter: AnimeGenrePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@GenreActivity)

        mAdapter = AnimeGenreAdapter()

        mPagingAdapter = AnimeGenrePagingAdapter()

        binding.apply {
            imgBack.setOnClickListener {
                finish()
            }

            tvGenreTitle.text = intent.getStringExtra(GENRE_TITLE)

//            viewModel.getAnimeByGenre(intent.getStringExtra(GENRE_ID).toString(), 1)
//            viewModel.anime.observe(this@GenreActivity) { result ->
//                when(result) {
//                    is Resource.Loading -> {
//                        progressBar.visibility = View.VISIBLE
//                    }
//                    is Resource.Success -> {
//                        mAdapter.submitList(result.data.animeList)
//                        rvGenres.apply {
//                            adapter = mAdapter
//                            setHasFixedSize(true)
//                        }
//                        progressBar.visibility = View.GONE
//                    }
//                    is Resource.Error -> {
//
//                    }
//                }
//            }

            val footerAdapter = AnimeLoadStateAdapter {
                mPagingAdapter.retry()
            }
            val layoutManager = GridLayoutManager(this@GenreActivity, 3)
            rvGenres.apply {
                setHasFixedSize(true)
                adapter = mPagingAdapter.withLoadStateFooter(
                    footer = footerAdapter
                )
                rvGenres.layoutManager = layoutManager
            }
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == mPagingAdapter.itemCount && footerAdapter.itemCount > 0) {
                        3
                    } else {
                        1
                    }
                }
            }

            viewModel.getAnimeByGenrePaging(intent.getStringExtra(GENRE_ID).toString())
            viewModel.animeList.observe(this@GenreActivity) { result ->
                mPagingAdapter.submitData(lifecycle, result)
            }
//            viewModel.animeList.observe(this@GenreActivity) { result ->
//                when(result) {
//                    is Resource.Loading -> {
//                        progressBar.visibility = View.VISIBLE
//                    }
//                    is Resource.Success -> {
//                        Log.d(TAG, "onCreate: ${result.data}")
//                        lifecycleScope.launch {
//                            mPagingAdapter.submitData(result.data)
//                        }
//                        rvGenres.apply {
//                            adapter = mPagingAdapter
//                            setHasFixedSize(true)
//                        }
//                        progressBar.visibility = View.GONE
//                    }
//                    is Resource.Error -> {
//                        Log.d(TAG, "onCreate: ${result.error}")
//                    }
//                }
//            }
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