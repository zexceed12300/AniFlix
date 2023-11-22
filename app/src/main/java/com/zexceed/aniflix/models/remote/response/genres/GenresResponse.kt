package com.zexceed.aniflix.models.remote.response.genres

import com.zexceed.aniflix.models.remote.response.Genre

data class GenresResponse(
    val genreList: List<Genre>
)