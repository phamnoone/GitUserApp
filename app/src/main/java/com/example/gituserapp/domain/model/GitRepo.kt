package com.example.gituserapp.domain.model

data class GitRepo(
    val id: Long?,
    val name: String?,
    val description: String?,
    val visibility: String?,
    val stargazersCount: Int?,
    val forksCount: Int?,
    val language: String?,
    val htmlUrl: String?
)
