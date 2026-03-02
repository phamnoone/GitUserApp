package com.example.gituserapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    data object UsersScreen: AppRoute()

    @Serializable
    data class UserDetailScreen(val username: String) : AppRoute()
}