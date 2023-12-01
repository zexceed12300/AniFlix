package com.zexceed.aniflix.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zexceed.aniflix.BuildConfig.API_BASE_URL
import com.zexceed.aniflix.apiservices.ApiConfig
import com.zexceed.aniflix.models.remote.response.complete.Anime

class CompletePagingSource : PagingSource<Int, Anime>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        val page = params.key ?: 1
        return try {
            val response = ApiConfig(API_BASE_URL).apiServices.getComplete(page)
            LoadResult.Page(
                data = response.animeList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.animeList.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PAGE_SIZE = 25 // Jumlah item dalam satu halaman
        const val PREFETCH_DISTANCE = 1 // Jarak di muka untuk memuat data tambahan
    }
}