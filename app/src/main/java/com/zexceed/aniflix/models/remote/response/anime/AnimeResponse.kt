package com.zexceed.aniflix.models.remote.response.anime

import com.zexceed.aniflix.models.remote.response.BatchLink
import com.zexceed.aniflix.models.remote.response.Episode
import com.zexceed.aniflix.models.remote.response.Genre

data class AnimeResponse(
    val anime_id: String,
    val batch_link: BatchLink,
    val duration: String,
    val episode_list: List<Episode>,
    val genre_list: List<Genre>,
    val japanase: String,
    val producer: String,
    val release_date: String,
    val score: Double,
    val status: String,
    val studio: String,
    val synopsis: String,
    val thumb: String,
    val title: String,
    val total_episode: Int,
    val type: String
)