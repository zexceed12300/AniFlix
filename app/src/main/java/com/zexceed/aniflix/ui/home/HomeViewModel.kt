package com.zexceed.aniflix.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.remote.response.home.HomeResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class HomeViewModel(
    application: Application
) : ViewModel() {

    private val mRespository: AniflixRepository = AniflixRepository(application)

    private lateinit var _home: LiveData<Resource<HomeResponse>>
    val home get() = _home

    init {
        getHome()
    }

    fun getHome() {
        _home = mRespository.getHome().asLiveData()
    }
}