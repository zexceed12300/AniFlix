package com.zexceed.aniflix.respository

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val error: String) : Resource<T>()
}
