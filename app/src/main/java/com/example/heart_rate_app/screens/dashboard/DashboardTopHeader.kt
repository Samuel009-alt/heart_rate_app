package com.example.heart_rate_app.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.heart_rate_app.viewmodel.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DashboardTopHeader(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    // Dynamic date formatting
    val currentDate = remember {
        val calendar = Calendar.getInstance()
        SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(calendar.time)
    }

    // Get actual user name from UserData
    val userName = currentUser?.fullName ?: "User"
    val displayName = if (userName != "User") userName.split(" ").firstOrNull() ?: "User" else "User"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        color = Color.Transparent
    ) {
        Column {
            // Greeting and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello $displayName",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2C3E50),
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = currentDate,
                        fontSize = 16.sp,
                        color = Color(0xFF6C757D),
                        fontWeight = FontWeight.Medium
                    )
                }

                // Profile image with Cloudinary integration
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clickable { navController.navigate("profile") },
                    contentAlignment = Alignment.Center
                ) {
                    if (!currentUser?.profileImageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = currentUser?.profileImageUrl,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFF8F9FA),
                                            Color(0xFFE9ECEF)
                                        )
                                    ),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (userName != "User") {
                                Text(
                                    text = userName.firstOrNull()?.toString()?.uppercase() ?: "U",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2C3E50)
                                )
                            } else {
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = "Profile",
                                    tint = Color(0xFF2C3E50),
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Enhanced Calendar
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    // Days of week
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                            Text(
                                text = day,
                                fontSize = 14.sp,
                                color = Color(0xFF0A3D62),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .width(32.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Current week dates with actual calendar logic
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val calendar = Calendar.getInstance()
                        val today = calendar.get(Calendar.DAY_OF_MONTH)
                        val currentWeekDates = getCurrentWeekDates()

                        currentWeekDates.forEach { date ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        brush = if (date == today) {
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    Color(0xFF0A3D62),
                                                    Color(0xFF2C5282)
                                                )
                                            )
                                        } else Brush.linearGradient(
                                            listOf(Color.Transparent, Color.Transparent)
                                        ),
                                        shape = CircleShape
                                    )
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = date.toString(),
                                    fontSize = 14.sp,
                                    color = if (date == today) Color.White else Color(0xFF495057),
                                    fontWeight = if (date == today) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper function to get current week dates
private fun getCurrentWeekDates(): List<Int> {
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_MONTH)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1 = Sunday, 7 = Saturday

    // Calculate the start of the week (Sunday)
    val startOfWeek = today - (dayOfWeek - 1)

    return (0..6).map { offset ->
        val date = startOfWeek + offset
        maxOf(1, minOf(date, 31)) // Keep dates within valid range
    }
}