package com.example.heart_rate_app.screens.dashboard


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.data.models.HeartRateReading
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.screens.dashboard.components.HeartRateCircle
import com.example.heart_rate_app.screens.dashboard.components.HistoryItem
import com.example.heart_rate_app.screens.dashboard.components.MeasurementButton
import com.example.heart_rate_app.screens.dashboard.components.StatisticsCard
import com.example.heart_rate_app.screens.dashboard.components.StatusText
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
// LESSON: Import your AuthViewModel
import com.example.heart_rate_app.viewmodel.AuthViewModel
import com.google.firebase.firestore.Query

@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    // LESSON: Get user data from ViewModel instead of navigation parameters
    val currentUser by authViewModel.currentUser.collectAsState()

    // Heart rate measurement state
    var currentHeartRate by remember { mutableStateOf(0) }
    var isMeasuring by remember { mutableStateOf(false) }
    var measurementProgress by remember { mutableStateOf(0f) }

    // History and data state
    var heartRateHistory by remember { mutableStateOf<List<HeartRateReading>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var isLoadingHistory by remember { mutableStateOf(false) }

    // Firebase Firestore instance
    val firestore = remember { FirebaseFirestore.getInstance() }

    // LESSON: Load Heart Rate History When User Changes
    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid.let { userId ->
            isLoadingHistory = true

            // FIXED: Load existing heart rate data from Firestore
            firestore.collection("heart_rate_readings")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(20) // Get last 20 readings
                .get()
                .addOnSuccessListener { documents ->
                    val readings = documents.mapNotNull { doc ->
                        try {
                            HeartRateReading(
                                bpm = doc.getLong("bpm")?.toInt() ?: 0,
                                timestamp = doc.getLong("timestamp") ?: 0L,
                                date = doc.getString("date") ?: ""
                            )
                        } catch (e: Exception){
                            null // Skip invalid documents
                        }
                    }
                    heartRateHistory = readings
                    isLoadingHistory = false
                }
                .addOnFailureListener { e ->
                    isLoadingHistory = false
                }
        }
    }

    // LESSON: Enhanced Measurement Logic with Data Saving
    LaunchedEffect(isMeasuring) {
        if (isMeasuring) {
            isLoading = true
            val measurementDuration = 10000L // 10 seconds
            val updateInterval = 100L // Update every 100ms
            val totalSteps = measurementDuration / updateInterval

            measurementProgress = 0f

            repeat(totalSteps.toInt()) { step ->
                delay(updateInterval)
                measurementProgress = (step + 1).toFloat() / totalSteps
                currentHeartRate = Random.nextInt(65, 95)

                // Check if user stopped measurement
                if (!isMeasuring) {
                    isLoading = false
                    return@LaunchedEffect
                }
            }

            // Final measurement
            val finalBpm = Random.nextInt(70, 85)
            currentHeartRate = finalBpm

            // LESSON: Save to Firebase Firestore
            val newReading = HeartRateReading(bpm = finalBpm)

            currentUser?.uid?.let { userId ->
                val dataToSave = mapOf(
                    "bpm" to newReading.bpm,
                    "timestamp" to newReading.timestamp,
                    "date" to newReading.date,
                    "userId" to userId // Associate with user
                )

                firestore.collection("heart_rate_readings")
                    .add(dataToSave)
                    .addOnSuccessListener {
                        // FIXED: Add to history immediately for UI update
                        heartRateHistory = listOf(newReading) + heartRateHistory
                        isLoading = false
                    }
                    .addOnFailureListener { e ->
                        // Fallback: still add locally so app doesn't break
                        heartRateHistory = listOf(newReading) + heartRateHistory
                        isLoading = false
                    }
            } ?: run {
                // Handle case where user ID is null
                heartRateHistory = listOf(newReading) + heartRateHistory
                isLoading = false
            }
            isMeasuring = false
        } else {
            // Reset when stopping measurement
            if (currentHeartRate > 0 && measurementProgress < 1f) {
                currentHeartRate = 0
                measurementProgress = 0f
            }
            isLoading = false
        }
    }

    // LESSON: Calculate Statistics
    // Compute average heart rate from history
    val averageHeartRate = if (heartRateHistory.isNotEmpty()) {
        heartRateHistory.map { it.bpm }.average().toInt()
    } else 0

    // LESSON: Scrollable Layout for More Content
    // LazyColumn allows efficient scrolling of large lists
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Message with User's Name
        item {
            Text(
                text = "Welcome, ${currentUser?.fullName ?: "User"}!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )
        }

        // App Title
        item {
            Text(
                text = "Heart Rate Monitor",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Heart Rate Display Circle
        item {
            HeartRateCircle(
                heartRate = currentHeartRate,
                progress = measurementProgress,
                isMeasuring = isMeasuring
            )
        }

        // Statistics Card
        item {
            StatisticsCard(
                totalReadings = heartRateHistory.size,
                averageHeartRate = averageHeartRate,
                lastReading = heartRateHistory.lastOrNull(),
                isLoading = isLoadingHistory
            )
        }

        // Control Button
        item {
            MeasurementButton(
                isMeasuring = isMeasuring,
                isLoading = isLoading,
                onToggleMeasuring = {
                    if (!isMeasuring) {
                        isMeasuring = true
                        currentHeartRate = 72 // Initial resting rate
                    } else {
                        isMeasuring = false
                        measurementProgress = 0f
                    }
                }
            )
        }

        // Status Text
        item {
            StatusText(
                isMeasuring = isMeasuring,
                isLoading = isLoading,
                progress = measurementProgress
            )
        }

        // History Section Header
        item {
            if (heartRateHistory.isNotEmpty()) {
                Text(
                    text = "Recent Measurements",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            } else if (isLoadingHistory){
                Text(
                    text = "Loading you history...",
                    fontSize = 16.sp,
                    color = Color(0xFF666666)
                )
            }
        }

        // History List - Show most recent 5 readings
        items(heartRateHistory.take(10)) { reading ->
            HistoryItem(reading = reading)
        }

        // History & Profile Navigation Buttons
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(Routes.HISTORY) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    Text("History")
                }

                Button(
                    onClick = { navController.navigate(Routes.PROFILE) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03A9F4),
                        contentColor = Color.White
                    )
                ) {
                    Text("Profile")
                }
            }
        }

        // Sign Out Button
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    authViewModel.signOut()
                    // Navigate back to sign in
                    navController.navigate(Routes.SIGN_IN) {
                        popUpTo(0) { inclusive = true } // Clear entire back stack
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Out", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}



