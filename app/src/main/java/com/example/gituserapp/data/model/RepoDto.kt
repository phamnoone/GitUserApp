package com.example.gituserapp.data.model

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("visibility") val visibility: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int?,
    @SerializedName("forks_count") val forksCount: Int?,
    @SerializedName("language") val language: String?,
    @SerializedName("html_url") val htmlUrl: String?
)
