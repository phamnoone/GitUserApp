package com.example.gituserapp.domain.usecase

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitRepo
import com.example.gituserapp.domain.model.GitUserDetail
import com.example.gituserapp.domain.repository.GitRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: GitRepository
) {
    suspend operator fun invoke(
        username: String,
        page: Int = 1
    ): ApiResponse<GitUserDetail> {
        return repository.getUserDetail(username)
    }
}
