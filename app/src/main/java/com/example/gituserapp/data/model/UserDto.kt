package com.example.gituserapp.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val avatarUrl: String?
)
