package com.zexceed.aniflix.models.remote.response.complete

data class Anime(
    val episode: String,
    val id: String,
    val link: String,
    val score: Double,
    val thumb: String,
    val title: String,
    val uploaded_on: String
)