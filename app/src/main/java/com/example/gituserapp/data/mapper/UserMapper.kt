package com.example.gituserapp.data.mapper

import com.example.gituserapp.data.model.UserDto
import com.example.gituserapp.domain.model.GitUser

fun UserDto.toDomain(): GitUser {
    return GitUser(
        id = this.id,
        name = this.login,
        avatarUrl = this.avatarUrl
    )
}
