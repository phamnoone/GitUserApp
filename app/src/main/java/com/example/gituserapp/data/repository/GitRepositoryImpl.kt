package com.example.gituserapp.data.repository

import com.example.gituserapp.data.api.GithubApi
import com.example.gituserapp.data.mapper.toDomain
import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitRepo
import com.example.gituserapp.domain.model.GitUser
import com.example.gituserapp.domain.model.GitUserDetail
import com.example.gituserapp.domain.repository.GitRepository
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(
    private val apiService: GithubApi,
) : GitRepository {

    override suspend fun getUsers(nextKey: Int): ApiResponse<List<GitUser>> {
        return apiService.getUsers(since = nextKey)
            .map { it.map { userDto -> userDto.toDomain() } }
    }

    override suspend fun searchUser(keyword: String, page: Int): ApiResponse<List<GitUser>> {
        return apiService.searchUser(query = keyword, page = page)
            .map { it.items.map { userDto -> userDto.toDomain() } }
    }

    override suspend fun getUserDetail(username: String): ApiResponse<GitUserDetail> {
        return apiService.getUserDetail(username)
            .map { it.toDomain() }
    }

    override suspend fun getUserRepos(username: String, page: Int): ApiResponse<List<GitRepo>> {
        return apiService.getUserRepos(username = username, page = page)
            .map { it.map { repoDto -> repoDto.toDomain() } }
    }
}
