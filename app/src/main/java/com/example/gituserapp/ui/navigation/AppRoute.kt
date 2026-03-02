package com.example.gituserapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    data object UsersScreen: AppRoute()
}