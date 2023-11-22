package com.zexceed.aniflix.models.remote.response.genre

import com.zexceed.aniflix.models.remote.response.Genre

data class Anime(
    val anime_name: String,
    val episode: String,
    val genre_list: List<Genre>,
    val id: String,
    val link: String,
    val release_date: String,
    val score: Double?,
    val studio: String,
    val thumb: String
)