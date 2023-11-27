package com.zexceed.aniflix.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zexceed.aniflix.models.local.room.HistoryEntity
import com.zexceed.aniflix.respository.AniflixRepository

class HistoryViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    fun getAllHistory() : LiveData<List<HistoryEntity>> {
        return mRepository.getAllHistory()
    }

    suspend fun deleteAllHistory() {
        mRepository.deleteAllHistory()
    }

}