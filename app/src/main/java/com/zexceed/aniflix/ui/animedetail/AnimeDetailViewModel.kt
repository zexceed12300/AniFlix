package com.zexceed.aniflix.ui.animedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.local.room.HistoryEntity
import com.zexceed.aniflix.models.local.room.MylistEntity
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

    fun getAnime(id: String) {
        _anime = mRepository.getAnime(id).asLiveData()
    }

    fun getEpisode(id: String) {
        _episode = mRepository.getEpisode(id).asLiveData()
    }

    fun getMylist(animeId: String) : LiveData<MylistEntity> {
        return mRepository.getMylistById(animeId)
    }

    suspend fun storeMylist(anime: MylistEntity) {
        mRepository.storeMylist(anime)
    }

    suspend fun deleteMylist(animeId: String) {
        mRepository.deleteMyList(animeId)
    }


    fun getHistoryById(animeId: String) : LiveData<HistoryEntity> {
        return mRepository.getHistoryById(animeId)
    }
    suspend fun insertHistory(history: HistoryEntity) {
        mRepository.insertHistory(history)
    }

}