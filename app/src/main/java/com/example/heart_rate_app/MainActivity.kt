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
import com.example.heart_rate_app.navigation.AppNavGraph
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.ui.theme.Heart_Rate_AppTheme
import com.example.heart_rate_app.utils.SessionManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            Heart_Rate_AppTheme {
//                val firebaseApp = FirebaseApp.getInstance()
//                Log.d("FirebaseTest", "Firebase Initialized: ${firebaseApp.name}")
//                val auth = FirebaseAuth.getInstance()
//                Log.d("FirebaseTest", "Current user: ${auth.currentUser}")



                val context = this
                val sessionManager = SessionManager(context)

                val isOnboardingCompleted by
                    sessionManager.isOnboardingCompleted.collectAsState(
                        initial = false
                    )

                val startDestination = if (isOnboardingCompleted) {
                    Routes.SIGN_IN
                } else {
                    Routes.ONBOARDING
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(
                        startDestination = startDestination
                            //if (
                                //isOnboardingCompleted
                            //) Routes.SIGN_IN else Routes.ONBOARDING
                    )
                }
            }
        }
    }
}
