package com.example.jikananimeexplorer.utils.result

sealed class Resource<T> {

    class Success<T>(val data: T) : Resource<T>()

    class Error<T>(
        val message: String,
        val throwable: Throwable? = null
    ) : Resource<T>()

    class Loading<T> : Resource<T>()
}
