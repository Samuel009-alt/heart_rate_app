package com.example.heart_rate_app.screens.dashboard

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.data.models.HeartRateReading
import com.example.heart_rate_app.data.models.HeartRateStatistics
import com.example.heart_rate_app.data.models.calculateStatistics
import com.example.heart_rate_app.data.repositories.HeartRateRepository
import com.example.heart_rate_app.screens.dashboard.components.HealthOverviewCard
import com.example.heart_rate_app.screens.dashboard.components.HeartRateMeasurementSection
import com.example.heart_rate_app.screens.dashboard.components.ModernBottomNavigation
import com.example.heart_rate_app.screens.dashboard.components.QuickStatsRow
import com.example.heart_rate_app.screens.dashboard.components.RecentReadingsCard
import com.example.heart_rate_app.ui.theme.YellowPrimary
import com.example.heart_rate_app.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlin.math.min

@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var currentHeartRate by remember { mutableStateOf(0) }
    var isMonitoring by remember { mutableStateOf(false) }
    var recentReadings by remember { mutableStateOf<List<HeartRateReading>>(emptyList()) }
    var statistics by remember { mutableStateOf(HeartRateStatistics()) }
    var isLoading by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf(false) }

    val repository = remember { HeartRateRepository() }
    val currentUser = FirebaseAuth.getInstance().currentUser

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

    // Load data when screen appears
    LaunchedEffect(Unit) {
        if (currentUser?.uid != null) {
            try {
                val readings = repository.getHeartRateHistory(currentUser.uid)
                val stats = calculateStatistics(readings)
                recentReadings = readings
                statistics = stats
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // Effect for simulating heart rate monitoring
    LaunchedEffect(isMonitoring) {
        if (isMonitoring) {
            // Reset heart rate when starting monitoring
            currentHeartRate = 0

            // Simulate heart rate measurement
            val targetBpm = (35..160).random()
            var currentBpm = 0

            // Gradual increase to simulate real measurement
            while (currentBpm < targetBpm && isMonitoring) {
                currentBpm = min(currentBpm + (8..20).random(), targetBpm)
                currentHeartRate = currentBpm
                delay(300) // Update every 300ms for smooth animation
            }

            // Hold the final value for 5 seconds, then auto-stop
            if (isMonitoring) {
                delay(5000)
                if (isMonitoring) {  // Check if still monitoring after delay
                    isMonitoring = false
                }
            }
        }
    }

    // Effect for saving reading after monitoring stops
    LaunchedEffect(isMonitoring) {
        if (!isMonitoring && currentHeartRate > 0) {
            isLoading = true
            saveError = false

            if (currentUser?.uid != null) {
                try {
                    val reading = HeartRateReading(
                        bpm = currentHeartRate,
                        timestamp = System.currentTimeMillis()
                    ).withCurrentDate()

                    val success = repository.saveHeartRateReading(currentUser.uid, reading)
                    if (success) {
                        // Refresh data after saving
                        val readings = repository.getHeartRateHistory(currentUser.uid)
                        val stats = calculateStatistics(readings)
                        recentReadings = readings
                        statistics = stats

                        // Also notify the ViewModel to refresh its state
                        authViewModel.fetchHeartRateHistory()
                    } else {
                        saveError = true
                    }
                } catch (e: Exception) {
                    saveError = true
                } finally {
                    isLoading = false
                }
            } else {
                isLoading = false
            }
        }
    }

    Scaffold(
        bottomBar = {
            ModernBottomNavigation(
                navController = navController,
                currentRoute = "dashboard",
                authViewModel = authViewModel
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Top Header Section with Calendar
                DashboardTopHeader(
                    navController = navController,
                    authViewModel = authViewModel
                )

                // Main Content
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        // Heart Rate Measurement Circle
                        HeartRateMeasurementSection(
                            currentHeartRate = currentHeartRate,
                            isMonitoring = isMonitoring,
                            isLoading = isLoading,
                            saveError = saveError,
                            onStartMonitoring = {
                                isMonitoring = true
                                isLoading = false
                                saveError = false
                            },
                            onStopMonitoring = {
                                isMonitoring = false
                            }
                        )
                    }

                    item {
                        // Health Overview
                        HealthOverviewCard(statistics = statistics)
                    }

                    item {
                        // Quick Stats Row
                        QuickStatsRow(statistics = statistics)
                    }

                    item {
                        // Recent Readings
                        RecentReadingsCard(
                            readings = recentReadings.take(5),
                            onViewAll = {
                                navController.navigate("history")
                            }
                        )
                    }
                }
            }
        }
    }
}
