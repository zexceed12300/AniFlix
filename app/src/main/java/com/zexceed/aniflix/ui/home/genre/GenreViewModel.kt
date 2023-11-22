package com.zexceed.aniflix.ui.home.genre

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.remote.response.genre.GenreResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class GenreViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _anime: LiveData<Resource<GenreResponse>>
    val anime get() = _anime

    fun getAnimeByGenre(genreId: String, page: Int) {
        _anime = mRepository.getAnimeByGenre(genreId, page).asLiveData()
    }
}