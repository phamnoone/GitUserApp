package com.example.gituserapp.domain.model

data class GitUserDetail(
    val id: Long?,
    val login: String?,
    val avatarUrl: String?,
    val name: String?,
    val bio: String?,
    val followers: Int?,
    val location: String?,
    val website: String?,
    val email: String?,
)
