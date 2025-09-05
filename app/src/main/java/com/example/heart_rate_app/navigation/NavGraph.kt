package com.example.heart_rate_app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.heart_rate_app.screens.auth.SignInScreen
import com.example.heart_rate_app.screens.auth.SignUpScreen
import com.example.heart_rate_app.screens.dashboard.DashboardScreen
import com.example.heart_rate_app.screens.history.HistoryScreen
import com.example.heart_rate_app.screens.onboarding.OnboardingScreen
import com.example.heart_rate_app.screens.profile.ProfileConfirmationScreen
import com.example.heart_rate_app.screens.profile.ProfileScreen
import com.example.heart_rate_app.viewmodel.AuthViewModel

@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel = viewModel(),
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        // Onboarding Screen
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                navController
            )
        }

        // Sign In Screen
        composable(Routes.SIGN_IN) {
            SignInScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Sign Up Screen
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Dashboard Screen
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable (Routes.HISTORY){
            HistoryScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable (Routes.PROFILE){
            ProfileScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable (Routes.PROFILE_CONFIRMATION){
            ProfileConfirmationScreen(
                navController = navController
            )
        }
    }
}