package com.zexceed.aniflix.respository

import android.app.Application
import android.util.Log
import com.zexceed.aniflix.BuildConfig
import com.zexceed.aniflix.apiservices.ApiConfig
import com.zexceed.aniflix.utils.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AniflixRepository(application: Application) {
    fun getHome() = flow {
        emit(Resources.Loading())
        val response = ApiConfig(BuildConfig.API_BASE_URL).apiServices.getHome()
        Log.d(TAG, "getHome: $response")
        emit(Resources.Success(response))
    }.catch {
        emit(Resources.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

}