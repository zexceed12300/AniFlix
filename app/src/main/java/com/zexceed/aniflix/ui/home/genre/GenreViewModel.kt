package com.zexceed.aniflix.ui.home.genre

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zexceed.aniflix.models.remote.response.genre.Anime
import com.zexceed.aniflix.models.remote.response.genre.GenreResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    private var _genre = MutableLiveData("")
    val genre get() = _genre

    private lateinit var _anime: LiveData<PagingData<Anime>>
    val anime get() = _anime

    fun getAnimeByGenrePaging(genreId: String) {
        if ( genre.value.isNullOrEmpty() || genre.value != genreId) {
            _genre.value = genreId
            _anime = mRepository.getAnimeByGenrePaging(genreId).cachedIn(viewModelScope)
        }
    }

}