package com.zexceed.aniflix.respository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.zexceed.aniflix.BuildConfig.API_BASE_URL
import com.zexceed.aniflix.apiservices.ApiConfig
import com.zexceed.aniflix.models.local.room.AniflixDatabase
import com.zexceed.aniflix.models.local.room.HistoryDao
import com.zexceed.aniflix.models.local.room.HistoryEntity
import com.zexceed.aniflix.models.local.room.MylistDao
import com.zexceed.aniflix.models.local.room.MylistEntity
import com.zexceed.aniflix.models.remote.response.genre.Anime
import com.zexceed.aniflix.paging.AnimeByGenrePagingSource
import com.zexceed.aniflix.paging.AnimeByGenrePagingSource.Companion.PAGE_SIZE
import com.zexceed.aniflix.paging.AnimeByGenrePagingSource.Companion.PREFETCH_DISTANCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AniflixRepository(application: Application) {

    private val mMylistDao: MylistDao
    private val mHistoryDao: HistoryDao

    init {
        val db = AniflixDatabase.getDatabase(application)
        mMylistDao = db.myListDao()
        mHistoryDao = db.historyDao()
    }

    fun getMylist(): LiveData<List<MylistEntity>> = mMylistDao.getMyList()

    fun getMylistById(animeId: String): LiveData<MylistEntity> = mMylistDao.getMyListById(animeId)

    suspend fun storeMylist(anime: MylistEntity) {
        mMylistDao.storeMyList(anime)
    }

    suspend fun deleteMyList(animeId: String) {
        mMylistDao.deleteMyListById(animeId)
    }

    fun getAllHistory(): LiveData<List<HistoryEntity>> = mHistoryDao.getAllHistory()
    fun getHistoryById(animeId: String): LiveData<HistoryEntity> = mHistoryDao.getHistoryById(animeId)
    suspend fun insertHistory(history: HistoryEntity) {
        mHistoryDao.insertHistory(history)
    }
    suspend fun deleteHistoryById(animeId: String) {
        mHistoryDao.deleteHistoryById(animeId)
    }

    suspend fun deleteAllHistory() {
        mHistoryDao.deleteAllHistory()
    }

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

    fun getGenre() = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getGenre()
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getAnimeByGenre(genreId: String, page: Int) = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getAnimeByGenre(genreId, page)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getAnimeByGenrePaging(genreId: String) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { AnimeByGenrePagingSource(genreId) }
        ).liveData

    fun getSchedule() = flow {
        emit(Resource.Loading())
        val response = ApiConfig(API_BASE_URL).apiServices.getSchedule()
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}