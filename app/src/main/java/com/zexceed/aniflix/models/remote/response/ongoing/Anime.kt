package com.zexceed.aniflix.models.remote.response.ongoing

data class Anime(
    val day_updated: String,
    val episode: String,
    val id: String,
    val link: String,
    val thumb: String,
    val title: String,
    val uploaded_on: String
)