package com.zexceed.aniflix.models.remote.response.home

data class Home(
    val complete: List<Complete>,
    val on_going: List<OnGoing>
)