package com.zexceed.aniflix.models.remote.response.episode

data class HighQuality(
    val download_links: List<DownloadLink>,
    val quality: String,
    val size: String
)