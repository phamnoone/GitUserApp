package com.example.gituserapp.data.model

import com.google.gson.annotations.SerializedName

data class UserDetailDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("location") val location: String?,
    @SerializedName("blog") val blog: String?,
    @SerializedName("twitter_username") val twitterUsername: String?,
    @SerializedName("email") val email: String?
)
