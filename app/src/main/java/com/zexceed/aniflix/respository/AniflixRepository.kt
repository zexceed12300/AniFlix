package com.zexceed.aniflix.respository

import android.app.Application
import com.zexceed.aniflix.BuildConfig.API_BASE_URL
import com.zexceed.aniflix.apiservices.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AniflixRepository(application: Application) {
    fun getHome() = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getHome()
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getSearchResult(query: String) = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getSearch(query)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getOngoing(page: Int) = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getOngoing(page)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getComplete(page: Int) = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getComplete(page)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getAnime(id: String) = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getAnime(id)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getEpisode(id: String) = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getEpisode(id)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}