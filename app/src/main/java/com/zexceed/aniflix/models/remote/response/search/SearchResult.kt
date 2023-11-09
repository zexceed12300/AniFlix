package com.zexceed.aniflix.models.remote.response.search

data class SearchResult(
    val genre_list: List<Genre>,
    val id: String,
    val link: String,
    val score: Double?,
    val status: String,
    val thumb: String,
    val title: String
)