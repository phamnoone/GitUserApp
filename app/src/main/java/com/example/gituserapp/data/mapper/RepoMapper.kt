package com.example.gituserapp.data.mapper

import com.example.gituserapp.data.model.RepoDto
import com.example.gituserapp.domain.model.GitRepo

fun RepoDto.toDomain(): GitRepo {
    return GitRepo(
        id = this.id,
        name = this.name,
        description = this.description,
        visibility = this.visibility,
        stargazersCount = this.stargazersCount,
        forksCount = this.forksCount,
        language = this.language,
        htmlUrl = this.htmlUrl
    )
}
