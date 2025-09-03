
package com.example.heart_rate_app.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.screens.dashboard.components.ActionButton
import com.example.heart_rate_app.screens.dashboard.components.HeartRateCircle
import com.example.heart_rate_app.screens.dashboard.components.HistoryItem
import com.example.heart_rate_app.screens.dashboard.components.MeasurementButton
import com.example.heart_rate_app.screens.dashboard.components.StatisticsCard
import com.example.heart_rate_app.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val heartRateHistory by authViewModel.heartRateHistory.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

    // Local measurement state
    var currentHeartRate by remember { mutableStateOf(0) }
    var isMeasuring by remember { mutableStateOf(false) }
    var measurementProgress by remember { mutableStateOf(0f) }

    // Load history when screen appears
    LaunchedEffect(Unit) {
        authViewModel.fetchHeartRateHistory()
    }

    // Measurement logic
    LaunchedEffect(isMeasuring) {
        if (isMeasuring) {
            val measurementDuration = 5000L
            val updateInterval = 100L
            val totalSteps = measurementDuration / updateInterval

            for (step in 0 until totalSteps.toInt()) {
                if (!isMeasuring) break
                delay(updateInterval)
                measurementProgress = (step + 1).toFloat() / totalSteps
                currentHeartRate = Random.nextInt(65, 95)
            }

            if (isMeasuring) {
                val finalBpm = Random.nextInt(70, 85)
                currentHeartRate = finalBpm
                authViewModel.saveHeartRateReading(finalBpm) { success ->
                    isMeasuring = false
                    measurementProgress = 0f
                }
            }
        }
    }

    // Calculate statistics
    val averageHeartRate = if (heartRateHistory.isNotEmpty()) {
        heartRateHistory.map { it.bpm }.average().toInt()
    } else 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Heart Rate Monitor",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFD))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Welcome Section
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Welcome back,",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            currentUser?.fullName ?: "User",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Heart Rate Circle
                item {
                    HeartRateCircle(
                        heartRate = currentHeartRate,
                        progress = measurementProgress,
                        isMeasuring = isMeasuring
                    )
                }

                // Status Text
                item {
                    Text(
                        text = when {
                            isMeasuring -> "Measuring... ${(measurementProgress * 100).toInt()}% complete"
                            currentHeartRate > 0 -> "Measurement complete!"
                            else -> "Ready to measure your heart rate"
                        },
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }

                // Measurement Button
                item {
                    MeasurementButton(
                        isMeasuring = isMeasuring,
                        isLoading = isLoading,
                        onToggleMeasuring = {
                            isMeasuring = !isMeasuring
                            if (!isMeasuring) {
                                measurementProgress = 0f
                            }
                        }
                    )
                }

                // Statistics Card
                item {
                    StatisticsCard(
                        totalReadings = heartRateHistory.size,
                        averageHeartRate = averageHeartRate,
                        lastReading = heartRateHistory.firstOrNull(),
                        isLoading = isLoading
                    )
                }

                // Quick Actions
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "Quick Actions",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ActionButton(
                                icon = Icons.Filled.ArrowForward,
                                text = "History",
                                onClick = { navController.navigate(Routes.HISTORY) }
                            )

                            ActionButton(
                                icon = Icons.Filled.Person,
                                text = "Profile",
                                onClick = { navController.navigate(Routes.PROFILE) }
                            )
                        }
                    }
                }

                // Recent Readings Preview
                if (heartRateHistory.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                "Recent Readings",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            heartRateHistory.take(3).forEach { reading ->
                                HistoryItem(reading = reading)
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            if (heartRateHistory.size > 3) {
                                TextButton(
                                    onClick = { navController.navigate(Routes.HISTORY) }
                                ) {
                                    Text("View All Readings →")
                                }
                            }
                        }
                    }
                }

                // Sign Out Button
                item {
                    Button(
                        onClick = {
                            authViewModel.signOut()
                            navController.navigate(Routes.SIGN_IN) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Sign Out")  // FIXED: Use Icons.Filled
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sign Out")
                    }
                }

                // Bottom Spacer
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}