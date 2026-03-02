package com.example.gituserapp.ui.base

import com.example.gituserapp.domain.model.ApiResponse

interface Input

interface Output

sealed interface UiState<out T> {
    data object Initial : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: ApiResponse.Error<*>) : UiState<Nothing>
}
