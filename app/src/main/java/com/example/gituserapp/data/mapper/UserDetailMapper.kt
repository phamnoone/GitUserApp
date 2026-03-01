package com.example.gituserapp.data.mapper

import com.example.gituserapp.data.model.UserDetailDto
import com.example.gituserapp.domain.model.GitUserDetail

fun UserDetailDto.toDomain(): GitUserDetail {
    return GitUserDetail(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl,
        name = this.name,
        bio = this.bio,
        followers = this.followers,
        location = this.location,
        website = this.blog,
        email = this.email,
    )
}
