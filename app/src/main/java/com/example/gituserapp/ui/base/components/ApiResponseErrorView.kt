package com.example.gituserapp.ui.base.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.gituserapp.domain.model.ApiResponse
import com.gituserapp.R

@Composable
fun ApiResponseErrorView(error: ApiResponse.Error<*>) {
    val message = when (error) {
        is ApiResponse.Error.HttpError -> stringResource(
            R.string.error_http,
            error.code,
            error.errorBody ?: ""
        )

        is ApiResponse.Error.NetworkError -> stringResource(R.string.error_network)
        is ApiResponse.Error.UnknownError -> stringResource(
            R.string.error_unknown,
            error.exception.message ?: ""
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center
        )
    }
}
