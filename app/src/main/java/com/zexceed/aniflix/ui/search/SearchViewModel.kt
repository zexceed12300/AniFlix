package com.zexceed.aniflix.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.remote.response.search.SearchResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class SearchViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _searchResult: LiveData<Resource<SearchResponse>>
    val searchResult get() = _searchResult

    init {
        getSearchResult("")
    }

    fun getSearchResult(query: String) {
        _searchResult = mRepository.getSearchResult(query).asLiveData()
    }
}