package com.zexceed.aniflix.ui.animelist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zexceed.aniflix.models.local.room.MylistEntity
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class AnimeListViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    fun getAllMylist() : LiveData<List<MylistEntity>> {
        return mRepository.getMylist()
    }
}