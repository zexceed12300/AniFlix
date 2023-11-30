package com.zexceed.aniflix.ui.home.genre

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zexceed.aniflix.adapter.AnimeGenreAdapter
import com.zexceed.aniflix.adapter.AnimeGenrePagingAdapter
import com.zexceed.aniflix.adapter.AnimeLoadStateAdapter
import com.zexceed.aniflix.databinding.ActivityGenreBinding
import com.zexceed.aniflix.utils.ViewModelFactory

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
            viewModel.anime.observe(this@GenreActivity) { result ->
                mPagingAdapter.submitData(lifecycle, result)
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