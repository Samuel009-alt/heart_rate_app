package com.example.heart_rate_app.screens.history

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.heart_rate_app.screens.dashboard.components.ModernBottomNavigation
import com.example.heart_rate_app.screens.history.component.AllReadingsSection
import com.example.heart_rate_app.screens.history.component.MainStatsCard
import com.example.heart_rate_app.screens.history.component.StatisticsSection
import com.example.heart_rate_app.ui.theme.YellowPrimary
import com.example.heart_rate_app.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

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

    val heartRateHistory by authViewModel.heartRateHistory.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.fetchHeartRateHistory()
    }

    // Calculate statistics
    val totalReadings = heartRateHistory.size
    val averageHeartRate = if (heartRateHistory.isNotEmpty()) {
        heartRateHistory.map { it.bpm }.average().toInt()
    } else 0
    val todayReadings = heartRateHistory.filter {
        // Assuming today's readings - you can implement proper date filtering
        true
    }.size

    Scaffold(
        bottomBar = {
            ModernBottomNavigation(
                navController = navController,
                currentRoute = "history"
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
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item { Spacer(modifier = Modifier.height(40.dp)) }

                // Header
                item {
                    HistoryHeader(
                        onBackClick = { navController.navigateUp() }
                    )
                }

                // Main Stats Card
                item {
                    MainStatsCard(
                        totalReadings = totalReadings,
                        averageHeartRate = averageHeartRate,
                        todayReadings = todayReadings,
                        highestReading = heartRateHistory.maxByOrNull { it.bpm }?.bpm ?: 0,
                        lowestReading = heartRateHistory.minByOrNull { it.bpm }?.bpm ?: 0
                    )
                }

                // Statistics Chart Section
                item {
                    StatisticsSection(readings = heartRateHistory)
                }

                // All Readings List
                item {
                    AllReadingsSection(readings = heartRateHistory)
                }

                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
fun HistoryHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackClick() }
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF2C3E50),
            )
        }

        Text(
            text = "History",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp,
            color = Color(0xFF2C3E50)
        )

        Spacer(modifier = Modifier.size(48.dp))
    }
}