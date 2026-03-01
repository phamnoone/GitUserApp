package com.example.gituserapp.domain.model

sealed class ApiResponse<T : Any> {
    class Success<T : Any>(val data: T) : ApiResponse<T>()
    sealed class Error<T : Any> : ApiResponse<T>() {
        class HttpError<T : Any>(val code: Int, val errorBody: String?) : Error<T>()
        class NetworkError<T : Any>(val exception: Throwable) : Error<T>()
        class UnknownError<T : Any>(val exception: Throwable) : Error<T>()
    }
}