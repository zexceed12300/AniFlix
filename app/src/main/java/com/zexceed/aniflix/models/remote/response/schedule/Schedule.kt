package com.zexceed.aniflix.models.remote.response.schedule

data class Schedule(
    val animeList: List<Anime>,
    val day: String
)