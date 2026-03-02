package com.example.gituserapp.ui.base

import androidx.compose.runtime.Composable
import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.ui.base.components.ApiResponseErrorView
import com.example.gituserapp.ui.base.components.DefaultLoadingView

@Composable
fun <T> BaseScreen(
    uiState: UiState<T>,
    onInitial: @Composable () -> Unit = { },
    onLoading: @Composable () -> Unit = {
        DefaultLoadingView()
    },
    onError: @Composable (error: ApiResponse.Error<*>) -> Unit = { error ->
        ApiResponseErrorView(error)
    },
    onSuccess: @Composable (data: T) -> Unit
) {
    when (uiState) {
        is UiState.Initial -> onInitial()
        is UiState.Loading -> onLoading()
        is UiState.Error -> onError(uiState.error)
        is UiState.Success -> onSuccess(uiState.data)
    }
}