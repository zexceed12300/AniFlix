package com.zexceed.aniflix.models.remote.response.episode

data class LowQuality(
    val download_links: List<DownloadLink>,
    val quality: String,
    val size: String
)