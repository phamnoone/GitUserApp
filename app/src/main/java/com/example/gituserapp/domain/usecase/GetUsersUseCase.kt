package com.example.gituserapp.domain.usecase

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitUser
import com.example.gituserapp.domain.repository.GitRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val gitRepository: GitRepository
) {
    suspend operator fun invoke(nextKey: Int): ApiResponse<List<GitUser>> {
        return gitRepository.getUsers(nextKey = nextKey)
    }
}
