package com.zexceed.aniflix.ui.home.schedule

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zexceed.aniflix.models.remote.response.anime.AnimeResponse
import com.zexceed.aniflix.models.remote.response.schedule.Schedule
import com.zexceed.aniflix.models.remote.response.schedule.ScheduleResponse
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.respository.Resource

class ScheduleViewModel(
    application: Application
) : ViewModel() {

    private val mRepository: AniflixRepository = AniflixRepository(application)

    private lateinit var _schedule: LiveData<Resource<ScheduleResponse>>
    val schedule get() = _schedule

    private lateinit var _schedule_anime: LiveData<Resource<AnimeResponse>>
    val schedule_anime get() = _schedule_anime

    init {
        getSchedule()
    }

    fun getSchedule() {
        _schedule = mRepository.getSchedule().asLiveData()
    }

    fun getAnime(id: String) {
        _schedule_anime = mRepository.getAnime(id).asLiveData()
    }

}