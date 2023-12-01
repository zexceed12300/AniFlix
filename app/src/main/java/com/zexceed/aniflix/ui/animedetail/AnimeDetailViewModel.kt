package com.zexceed.aniflix.ui.animedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.local.room.HistoryEntity
import com.zexceed.aniflix.models.local.room.MylistEntity
import com.zexceed.aniflix.models.remote.response.anime.AnimeResponse
import com.zexceed.aniflix.models.remote.response.episode.EpisodeResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.utils.Constants.TAG

class AnimeDetailViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    private var _animeId = MutableLiveData("")
    val animeId get() = _animeId
    private lateinit var _anime: LiveData<Resource<AnimeResponse>>
    val anime get() = _anime
    fun getAnime(id: String) {
        if (_animeId.value.isNullOrEmpty() || _animeId.value != id) {
            Log.d(TAG, "getAnime: triggered!!")
            _anime = mRepository.getAnime(id).asLiveData()
            _animeId.value = id
        }
    }

    private var _episodeId = MutableLiveData("")
    val episodeId get() = _episodeId
    private lateinit var _episode: LiveData<Resource<EpisodeResponse>>
    val episode get() = _episode
    fun getEpisode(id: String) {
        if (_episodeId.value.isNullOrEmpty() || _episodeId.value != id ) {
            Log.d(TAG, "getEpisode: triggered!! ${_episodeId.value} == ${id} ")
            _episode = mRepository.getEpisode(id).asLiveData()
            _episodeId.value = id
        }
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