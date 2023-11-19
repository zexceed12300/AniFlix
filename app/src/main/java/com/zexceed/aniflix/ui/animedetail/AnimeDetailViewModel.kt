package com.zexceed.aniflix.ui.animedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.remote.response.anime.AnimeResponse
import com.zexceed.aniflix.models.remote.response.episode.EpisodeResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class AnimeDetailViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _anime: LiveData<Resource<AnimeResponse>>
    val anime get() = _anime

    private lateinit var _episode: LiveData<Resource<EpisodeResponse>>
    val episode get() = _episode

    init {
        getAnime("Dawd")
    }

    fun getAnime(id: String) {
        _anime = mRepository.getAnime(id).asLiveData()
    }

    fun getEpisode(id: String) {
        _episode = mRepository.getEpisode(id).asLiveData()
    }

}