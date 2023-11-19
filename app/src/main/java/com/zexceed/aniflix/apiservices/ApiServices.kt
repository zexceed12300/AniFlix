package com.zexceed.aniflix.apiservices

import com.zexceed.aniflix.models.remote.response.anime.AnimeResponse
import com.zexceed.aniflix.models.remote.response.complete.CompleteResponse
import com.zexceed.aniflix.models.remote.response.episode.EpisodeResponse
import com.zexceed.aniflix.models.remote.response.home.HomeResponse
import com.zexceed.aniflix.models.remote.response.ongoing.OngoingResponse
import com.zexceed.aniflix.models.remote.response.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("home")
    suspend fun getHome() : HomeResponse

    @GET("search/{query}")
    suspend fun getSearch(
        @Path("query") query: String
    ) : SearchResponse

    @GET("ongoing/page/{page}")
    suspend fun getOngoing(
        @Path("page") page: Int
    ) : OngoingResponse

    @GET("complete/page/{page}")
    suspend fun getComplete(
        @Path("page") page: Int
    ) : CompleteResponse

    @GET("anime/{id}")
    suspend fun getAnime(
        @Path("id") id: String
    ) : AnimeResponse

    @GET("eps/{id}")
    suspend fun getEpisode(
        @Path("id") id: String
    ) : EpisodeResponse
}