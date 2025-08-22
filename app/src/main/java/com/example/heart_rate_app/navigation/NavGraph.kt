package com.example.heart_rate_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.heart_rate_app.screens.auth.SignInScreen
import com.example.heart_rate_app.screens.auth.SignUpScreen

sealed class Screen(val route: String) {
    object SignUpScreen : Screen("sign_up")
    object SignInScreen : Screen("sign_in")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SignUpScreen.route) {
        composable(Screen.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(Screen.SignInScreen.route) {
            SignInScreen(navController)
        }
    }
}