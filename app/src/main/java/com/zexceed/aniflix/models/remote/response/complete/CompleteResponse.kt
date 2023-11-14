package com.zexceed.aniflix.models.remote.response.complete

data class CompleteResponse(
    val animeList: List<Anime>,
    val baseUrl: String,
    val status: String
)