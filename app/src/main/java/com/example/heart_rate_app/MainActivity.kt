package com.example.heart_rate_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heart_rate_app.navigation.AppNavGraph
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.ui.theme.Heart_Rate_AppTheme
import com.example.heart_rate_app.utils.SessionManager
import com.example.heart_rate_app.viewmodel.AuthViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Heart_Rate_AppTheme {

                val context = this
                val sessionManager = SessionManager(context)
                val authViewModel: AuthViewModel = viewModel()

                val isOnboardingCompleted by
                sessionManager.isOnboardingCompleted.collectAsState(
                    initial = false
                )

                // COMBINE both conditions: onboarding completed AND login state
                val startDestination = when {
                    authViewModel.isUserLoggedIn() -> Routes.DASHBOARD
                    isOnboardingCompleted -> Routes.SIGN_IN
                    else -> Routes.ONBOARDING
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(startDestination = startDestination)
                }
            }
        }
    }
}
