package com.zexceed.aniflix.ui.home.ongoing

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.remote.response.ongoing.OngoingResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class OngoingViewModel(
    application: Application
) : ViewModel() {
    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _ongoing: LiveData<Resource<OngoingResponse>>
    val ongoing get() = _ongoing

    init {
        getOngoing(1)
    }

    fun getOngoing(page: Int) {
        _ongoing = mRepository.getOngoing(page).asLiveData()
    }
}