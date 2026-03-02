package com.example.gituserapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.gituserapp.ui.screen.users.UsersScreen

import com.example.gituserapp.ui.screen.detail.UserDetailScreen
import androidx.core.net.toUri

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoute.UsersScreen) {
        composable<AppRoute.UsersScreen> {
            UsersScreen(
                onUserClick = { username ->
                    navController.navigate(AppRoute.UserDetailScreen(username = username))
                }
            )
        }

        composable<AppRoute.UserDetailScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<AppRoute.UserDetailScreen>()
            val context = LocalContext.current
            UserDetailScreen(
                username = route.username,
                onBackClick = {
                    navController.popBackStack()
                },
                onRepoClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }
}