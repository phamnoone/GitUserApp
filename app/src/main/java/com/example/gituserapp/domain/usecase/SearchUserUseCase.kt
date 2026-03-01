package com.example.gituserapp.domain.usecase

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitUser
import com.example.gituserapp.domain.repository.GitRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val repository: GitRepository
) {
    suspend operator fun invoke(keyword: String, page: Int): ApiResponse<List<GitUser>> {
        return if (keyword.isBlank()) {
            repository.getUsers(page)
        } else {
            repository.searchUser(keyword, page)
        }
    }
}
