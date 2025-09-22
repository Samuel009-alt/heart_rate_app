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
import com.example.heart_rate_app.screens.onboarding.SplashScreen
import com.example.heart_rate_app.screens.profile.EditProfileScreen
import com.example.heart_rate_app.screens.profile.ProfileConfirmationScreen
import com.example.heart_rate_app.screens.profile.ProfileScreen
import com.example.heart_rate_app.viewmodel.AuthViewModel

@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel = viewModel(),
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH_SCREEN) {
        // Splash Screen
        composable (Routes.SPLASH_SCREEN){
            SplashScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Onboarding Screen
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                navController = navController
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

        // History Screen
        composable (Routes.HISTORY){
            HistoryScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        // Profile Screen
        composable (Routes.PROFILE){
            ProfileScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        // Profile Confirmation Screen
        composable (Routes.EDIT_PROFILE){
            EditProfileScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Profile Confirmation Screen
        composable (Routes.PROFILE_CONFIRMATION){
            ProfileConfirmationScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}