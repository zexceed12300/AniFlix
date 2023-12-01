package com.zexceed.aniflix.ui.home.ongoing

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zexceed.aniflix.models.remote.response.ongoing.Anime
import com.zexceed.aniflix.models.remote.response.ongoing.OngoingResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class OngoingViewModel(
    application: Application
) : ViewModel() {
    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _ongoing: LiveData<PagingData<Anime>>
    val ongoing get() = _ongoing

    init {
        getOngoing()
    }

    fun getOngoing() {
        _ongoing = mRepository.getOngoing().cachedIn(viewModelScope)
    }
}