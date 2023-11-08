package com.zexceed.aniflix.apiservices

import com.zexceed.aniflix.models.remote.response.home.HomeResponse
import retrofit2.http.GET

interface ApiServices {
    @GET("home")
    suspend fun getHome() : HomeResponse
}