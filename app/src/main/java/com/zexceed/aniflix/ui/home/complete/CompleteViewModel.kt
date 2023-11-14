package com.zexceed.aniflix.ui.home.complete

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.remote.response.complete.CompleteResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class CompleteViewModel(
    application: Application
) : ViewModel() {
    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _complete: LiveData<Resource<CompleteResponse>>
    val complete get() = _complete

    init {
        getComplete(1)
    }

    fun getComplete(page: Int) {
        _complete = mRepository.getComplete(page).asLiveData()
    }

}