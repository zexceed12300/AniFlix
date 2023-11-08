package com.zexceed.aniflix.respository

sealed class Resources<T> {
    class Loading<T> : Resources<T>()
    class Success<T>(val data: T) : Resources<T>()
    class Error<T>(val error: String) : Resources<T>()
}
