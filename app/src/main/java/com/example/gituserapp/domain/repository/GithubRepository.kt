package com.example.gituserapp.domain.repository

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitRepo
import com.example.gituserapp.domain.model.GitUser
import com.example.gituserapp.domain.model.GitUserDetail


interface GitRepository {
    suspend fun getUsers(nextKey: Int): ApiResponse<List<GitUser>>

    suspend fun searchUser(keyword: String, page: Int): ApiResponse<List<GitUser>>

    suspend fun getUserDetail(username: String): ApiResponse<GitUserDetail>

    suspend fun getUserRepos(username: String, page: Int): ApiResponse<List<GitRepo>>
}
