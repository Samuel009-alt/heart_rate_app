package com.example.heart_rate_app.screens.profile

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.screens.dashboard.components.ModernBottomNavigation
import com.example.heart_rate_app.screens.profile.components.HealthMetricsSection
import com.example.heart_rate_app.screens.profile.components.PersonalInformationSection
import com.example.heart_rate_app.screens.profile.components.ProfileHeader
import com.example.heart_rate_app.screens.profile.components.ProfileInfoCard
import com.example.heart_rate_app.screens.profile.components.SignOutSection
import com.example.heart_rate_app.ui.theme.Red
import com.example.heart_rate_app.ui.theme.SoftBlack
import com.example.heart_rate_app.ui.theme.YellowPrimary
import com.example.heart_rate_app.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val heartRateHistory by authViewModel.heartRateHistory.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        authViewModel.fetchHeartRateHistory()
    }

    // Animation states
    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "scale"
    )
    val rotationAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 360f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing),
        label = "rotation"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Scaffold(
        bottomBar = {
            ModernBottomNavigation(
                navController = navController,
                currentRoute = "profile"
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8F9FA),
                            Color(0xFFE9ECEF)
                        )
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = paddingValues.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item { Spacer(modifier = Modifier.height(40.dp)) }

                // Header
                item {
                    ProfileHeader(
                        onBackClick = { navController.navigateUp() }
                    )
                }

                // Profile Info Card
                item {
                    ProfileInfoCard(
                        user = currentUser,
                        totalReadings = heartRateHistory.size
                    )
                }

                // Personal Information Section (without card)
                item {
                    PersonalInformationSection(
                        user = currentUser,
                        onEditClick = { navController.navigate("edit_profile") }
                    )
                }

                // Health Metrics
                item {
                    HealthMetricsSection(readings = heartRateHistory)
                }

                // Sign Out Section
                item {
                    SignOutSection(
                        onSignOutClick = {
                            showLogoutDialog = true
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(15.dp)) }
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.ExitToApp,
                        contentDescription = null,
                        tint = Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign Out",
                        fontWeight = FontWeight.Bold,
                        color = SoftBlack
                    )
                }
            },
            text = {
                Text(
                    text = "Are you sure you want to sign out of your account?",
                    color = Color(0xFF6C757D),
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        authViewModel.signOut()
                        navController.navigate(Routes.SIGN_IN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlack
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Sign Out", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SoftBlack
                    ),
                    border = BorderStroke(1.dp, Color(0xFFDEE2E6)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Cancel", fontWeight = FontWeight.SemiBold)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}
