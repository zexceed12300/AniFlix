package com.zexceed.aniflix.ui.home.genre

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.zexceed.aniflix.models.remote.response.genre.Anime
import com.zexceed.aniflix.models.remote.response.genre.GenreResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource
import kotlinx.coroutines.launch

class GenreViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _anime: LiveData<Resource<GenreResponse>>
    val anime get() = _anime

    private lateinit var _animeList: LiveData<PagingData<Anime>>
    val animeList get() = _animeList

    fun getAnimeByGenrePaging(genreId: String) {
        _animeList = mRepository.getAnimeByGenrePaging(genreId)
    }

    fun getAnimeByGenre(genreId: String, page: Int) {
        _anime = mRepository.getAnimeByGenre(genreId, page).asLiveData()
    }
}