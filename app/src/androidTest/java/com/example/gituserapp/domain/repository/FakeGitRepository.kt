package com.example.gituserapp.domain.repository

import com.example.gituserapp.domain.model.ApiResponse
import com.example.gituserapp.domain.model.GitRepo
import com.example.gituserapp.domain.model.GitUser
import com.example.gituserapp.domain.model.GitUserDetail
import javax.inject.Inject

class FakeGitRepository @Inject constructor() : GitRepository {

    override suspend fun getUsers(nextKey: Int): ApiResponse<List<GitUser>> {
        return ApiResponse.Success(emptyList()) // Default behavior for non-search home
    }

    override suspend fun searchUser(query: String, page: Int): ApiResponse<List<GitUser>> {
        return when (query) {
            "google" -> {
                if (page == 1) {
                    ApiResponse.Success(
                        listOf(
                            GitUser(1, "google", "https://avatars.githubusercontent.com/u/1342004?v=4"),
                            GitUser(2, "google-research", "https://avatars.githubusercontent.com/u/163806?v=4")
                        )
                    )
                } else if (page == 2) {
                    ApiResponse.Success(
                        listOf(
                            GitUser(3, "google-api", "https://avatars.githubusercontent.com/u/152345?v=4")
                        )
                    )
                } else {
                    ApiResponse.Success(emptyList())
                }
            }
            "gzza1" -> {
                ApiResponse.Success(emptyList())
            }
            else -> ApiResponse.Success(emptyList())
        }
    }

    override suspend fun getUserDetail(username: String): ApiResponse<GitUserDetail> {
        return if (username == "google") {
            ApiResponse.Success(
                GitUserDetail(
                    id = 1,
                    login = "google",
                    avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                    name = "Google",
                    bio = "Google open source.",
                    followers = 1000,
                    location = "Mountain View",
                    website = "https://opensource.google.com/",
                    email = "opensource@google.com"
                )
            )
        } else {
            ApiResponse.Error.HttpError(404, "Not Found")
        }
    }

    override suspend fun getUserRepos(username: String, page: Int): ApiResponse<List<GitRepo>> {
        return if (username == "google") {
            if (page == 1) {
                ApiResponse.Success(
                    listOf(
                        GitRepo(1, "guava", "Google Core Libraries for Java", "public", 40000, 10000, "Java", "https://github.com/google/guava"),
                        GitRepo(2, "gson", "A Java serialization/deserialization library", "public", 20000, 5000, "Java", "https://github.com/google/gson")
                    )
                )
            } else if (page == 2) {
                ApiResponse.Success(
                    listOf(
                        GitRepo(3, "protobuf", "Protocol Buffers", "public", 60000, 15000, "C++", "https://github.com/google/protobuf")
                    )
                )
            } else {
                ApiResponse.Success(emptyList())
            }
        } else {
            ApiResponse.Success(emptyList())
        }
    }
}
