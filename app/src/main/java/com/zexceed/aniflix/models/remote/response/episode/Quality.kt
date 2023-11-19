package com.zexceed.aniflix.models.remote.response.episode

data class Quality(
    val high_quality: HighQuality,
    val low_quality: LowQuality,
    val medium_quality: MediumQuality
)