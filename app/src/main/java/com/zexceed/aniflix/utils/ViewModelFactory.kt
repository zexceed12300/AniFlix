package com.zexceed.aniflix.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zexceed.aniflix.respository.AniflixRepository
import com.zexceed.aniflix.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(
    private val application: Application,
    //private val repository: AniflixRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            //repository: AniflixRepository,
            application: Application
        ) : ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, /*repository*/)
                }
            }

            return INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application) as T
        }
        throw IllegalArgumentException("ViewModel class:: ${modelClass.name}")
    }

}