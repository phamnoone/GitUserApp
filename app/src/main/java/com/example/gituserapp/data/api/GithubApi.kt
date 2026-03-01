package com.example.gituserapp.data.api

import com.example.gituserapp.data.model.RepoDto
import com.example.gituserapp.data.model.SearchUserResponseDto
import com.example.gituserapp.data.model.UserDetailDto
import com.example.gituserapp.data.model.UserDto
import com.example.gituserapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int?,
    ): ApiResponse<List<UserDto>>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Query("page") page: Int?,
    ): ApiResponse<SearchUserResponseDto>

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): ApiResponse<UserDetailDto>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("page") page: Int?,
    ): ApiResponse<List<RepoDto>>
}
