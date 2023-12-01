package com.zexceed.aniflix.ui.home.complete

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.zexceed.aniflix.models.remote.response.complete.Anime
import com.zexceed.aniflix.models.remote.response.complete.CompleteResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class CompleteViewModel(
    application: Application
) : ViewModel() {
    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _complete: LiveData<PagingData<Anime>>
    val complete get() = _complete

    init {
        getComplete()
    }

    fun getComplete() {
        _complete = mRepository.getComplete()
    }

}