package com.zexceed.aniflix.models.remote.response.ongoing

data class OngoingResponse(
    val animeList: List<Anime>,
    val baseUrl: String,
    val status: String
)