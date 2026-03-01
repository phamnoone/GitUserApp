package com.example.gituserapp.domain.model

sealed class ApiResponse<T : Any> {
    class Success<T : Any>(val data: T) : ApiResponse<T>()
    sealed class Error<T : Any> : ApiResponse<T>() {
        class HttpError<T : Any>(val code: Int, val errorBody: String?) : Error<T>()
        class NetworkError<T : Any>(val exception: Throwable) : Error<T>()
        class UnknownError<T : Any>(val exception: Throwable) : Error<T>()
    }

    fun <R : Any> map(transform: (T) -> R): ApiResponse<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error.HttpError -> Error.HttpError(code, errorBody)
            is Error.NetworkError -> Error.NetworkError(exception)
            is Error.UnknownError -> Error.UnknownError(exception)
        }
    }
}