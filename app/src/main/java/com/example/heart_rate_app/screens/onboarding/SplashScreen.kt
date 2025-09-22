package com.example.heart_rate_app.screens.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.R
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.ui.theme.SoftBlack
import com.example.heart_rate_app.ui.theme.YellowPrimary
import com.example.heart_rate_app.utils.SessionManager
import com.example.heart_rate_app.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
){
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val isOnboardingCompleted by sessionManager.isOnboardingCompleted.collectAsState(initial = false)

    // Animation states
    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    val rotationAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 360f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
    )

    // SINGLE LaunchedEffect to handle both animation and navigation
    LaunchedEffect(key1 = true) {
        startAnimation = true

        delay(2500) // Show splash for 2.5 seconds

        val isLoggedIn = authViewModel.isUserLoggedIn()

        // Decide where to navigate based on user state
        val destination = when {
            isLoggedIn -> Routes.DASHBOARD
            isOnboardingCompleted -> Routes.SIGN_IN
            else -> Routes.ONBOARDING
        }

        navController.navigate(destination) {
            popUpTo(Routes.SPLASH_SCREEN) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFAFAFA),
                        Color(0xFFF5F5F5),
                        Color(0xFFEEEEEE)
                    ),
                    center = Offset(0.3f, 0.2f),
                    radius = 1000f
                )
            )
    ) {
        // Enhanced decorative circles with gradients and animations
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 280.dp, y = (-60).dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            YellowPrimary.copy(alpha = 0.8f),
                            YellowPrimary.copy(alpha = 0.4f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.8f
                    scaleY = scaleAnimation * 0.8f
                    rotationZ = rotationAnimation * 0.3f
                }
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (-30).dp, y = 100.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF3498DB).copy(alpha = 0.6f),
                            Color(0xFF3498DB).copy(alpha = 0.2f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.9f
                    scaleY = scaleAnimation * 0.9f
                    rotationZ = -rotationAnimation * 0.5f
                }
        )

        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(x = (-40).dp, y = 580.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            YellowPrimary.copy(alpha = 0.7f),
                            YellowPrimary.copy(alpha = 0.3f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.7f
                    scaleY = scaleAnimation * 0.7f
                    rotationZ = rotationAnimation * 0.4f
                }
        )

        Box(
            modifier = Modifier
                .size(90.dp)
                .offset(x = 320.dp, y = 450.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE74C3C).copy(alpha = 0.5f),
                            Color(0xFFE74C3C).copy(alpha = 0.2f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation
                    scaleY = scaleAnimation
                    rotationZ = -rotationAnimation * 0.6f
                }
        )

        Box(
            modifier = Modifier
                .size(110.dp)
                .offset(x = 250.dp, y = 650.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF27AE60).copy(alpha = 0.4f),
                            Color(0xFF27AE60).copy(alpha = 0.1f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.6f
                    scaleY = scaleAnimation * 0.6f
                    rotationZ = rotationAnimation * 0.7f
                }
        )

        Box (
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            // Center content
            Column(
                modifier = Modifier
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_heart_main_logo),
                        contentDescription = "Heart Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .scale(scaleAnimation)
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                    // App name with animation
                    Text(
                        text = "HR.",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SoftBlack,
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "H E A R T R A T E  M O N I T O R",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF6C757D),
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        }

        // Loading indicator at bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Color.White.copy(alpha = 0.9f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp),
                        color = YellowPrimary,
                        strokeWidth = 3.dp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Loading...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6C757D),
                    letterSpacing = 1.sp
                )
            }
        }

        // Subtle brand tagline
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your Health, Your Data, Your Control",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF95A5A6),
                letterSpacing = 0.5.sp
            )
        }
    }
}