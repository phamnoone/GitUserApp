package com.example.gituserapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gituserapp.ui.screen.users.UsersScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoute.UsersScreen) {
        composable<AppRoute.UsersScreen> {
            UsersScreen()
        }
    }
}