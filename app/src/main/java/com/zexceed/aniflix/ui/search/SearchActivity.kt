package com.zexceed.aniflix.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.zexceed.aniflix.adapter.SearchAdapter
import com.zexceed.aniflix.databinding.ActivitySearchBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.ViewModelFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var mAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@SearchActivity)

        mAdapter = SearchAdapter()

        binding.apply {

            imgBack.setOnClickListener {
                finish()
            }

            etSearch.requestFocus()

            etSearch.addTextChangedListener {
                viewModel.getSearchResult(etSearch.text.toString().trim())
                setList()
            }
        }
    }

    private fun setList() {
        viewModel.searchResult.observe(this@SearchActivity) { result ->
            when(result) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    mAdapter.submitList(result.data.search_results)
                    binding.rvSearchResult.apply {
                        adapter = mAdapter
                        setHasFixedSize(true)
                    }
                }

                is Resource.Error -> {
                    Log.d(TAG, "setList: ${result.error}")
                }
            }
        }
    }

    private fun obtainViewModel(activity: SearchActivity): SearchViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SearchViewModel::class.java]
    }
}