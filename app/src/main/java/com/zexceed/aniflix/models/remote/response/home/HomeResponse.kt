package com.zexceed.aniflix.models.remote.response.home

data class HomeResponse(
    val baseUrl: String,
    val home: Home,
    val status: String
)