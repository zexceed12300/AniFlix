package com.zexceed.aniflix.models.remote.response.search

data class SearchResponse(
    val baseUrl: String,
    val search_results: List<SearchResult>,
    val status: String
)