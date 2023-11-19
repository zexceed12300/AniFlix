package com.zexceed.aniflix.models.remote.response.episode

data class EpisodeResponse(
    val baseUrl: String,
    val id: String,
    val link_stream: String,
    val mirror1: Mirror1,
    val mirror2: Mirror2,
    val mirror3: Mirror3,
    val quality: Quality,
    val title: String
)