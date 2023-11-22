package com.zexceed.aniflix.models.remote.response.genre

data class GenreResponse(
    val animeList: List<Anime>,
    val baseUrl: String,
    val status: String
)