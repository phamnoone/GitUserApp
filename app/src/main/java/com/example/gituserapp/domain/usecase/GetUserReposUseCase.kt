package com.example.gituserapp.domain.usecase

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitRepo
import com.example.gituserapp.domain.repository.GitRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GitRepository
) {
    suspend operator fun invoke(
        username: String,
        page: Int
    ): ApiResponse<List<GitRepo>> {
        return repository.getUserRepos(username, page)
    }
}
