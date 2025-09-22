package com.example.heart_rate_app.screens.history.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.TrendingDown
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heart_rate_app.ui.theme.YellowPrimary

@Composable
fun MainStatsCard(
    totalReadings: Int,
    averageHeartRate: Int,
    todayReadings: Int,
    highestReading: Int,
    lowestReading: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEFEFEF),
                            Color(0xFFFFFFFF)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
                .padding(28.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    Icon(
                        Icons.Outlined.Analytics,
                        contentDescription = null,
                        tint = Color(0xFF3498DB).copy(alpha = 0.9f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "You have recorded",
                        fontSize = 16.sp,
                        color = Color(0xFF0A3D62).copy(alpha = 0.9f),
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Text(
                        text = "$totalReadings",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF27AE60),
                        letterSpacing = (-1).sp
                    )
                    Text(
                        text = " readings total",
                        fontSize = 16.sp,
                        color = Color(0xFF6C757D).copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Enhanced Circular Progress
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(140.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Canvas(modifier = Modifier.size(140.dp)) {
                        // Background circle
                        drawCircle(
                            color = Color.White.copy(alpha = 0.2f),
                            radius = size.minDimension / 2 - 8.dp.toPx(),
                            style = Stroke(width = 10.dp.toPx())
                        )

                        // Progress arc
                        val progress = if (averageHeartRate > 0) {
                            ((averageHeartRate - 40).toFloat() / 80f).coerceIn(0f, 1f)
                        } else 0f

                        drawArc(
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color(0xFFFF6B35),
                                    Color(0xFFFF8E53),
                                    Color(0xFFFF6B35)

                                )
                            ),
                            startAngle = -90f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            style = Stroke(
                                width = 10.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (averageHeartRate > 0) "$averageHeartRate" else "--",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (averageHeartRate > 0) Color(0xFFE74C3C) else Color(0xFF2196F3),
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = "Avg BPM",
                            fontSize = 13.sp,
                            color = Color(0xFF6C757D).copy(alpha = 0.9f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Readings Analyzed",
                            fontSize = 12.sp,
                            color = Color(0xFF6C757D).copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${totalReadings}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0A3D62)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Health Goal",
                            fontSize = 12.sp,
                            color = Color(0xFF6C757D).copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "60-100 BPM",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF121212)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    item {
                        SmallStatCard(
                            title = "TODAY",
                            value = "$todayReadings",
                            subtitle = "readings",
                            icon = Icons.Outlined.Today,
                            color = YellowPrimary
                        )
                    }
                    item {
                        SmallStatCard(
                            title = "AVERAGE",
                            value = if (averageHeartRate > 0) "$averageHeartRate" else "--",
                            subtitle = "BPM",
                            icon = Icons.Filled.Favorite,
                            color = Color(0xFF3498DB)
                        )
                    }
                    item {
                        SmallStatCard(
                            title = "HIGHEST",
                            value = if (highestReading > 0) "$highestReading" else "--",
                            subtitle = "BPM",
                            icon = Icons.Outlined.TrendingUp,
                            Color(0xFFFF4444)
                        )
                    }
                    item {
                        SmallStatCard(
                            title = "LOWEST",
                            value = if (lowestReading > 0) "$lowestReading" else "--",
                            subtitle = "BPM",
                            icon = Icons.Outlined.TrendingDown,
                            color = Color(0xFF27AE60)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SmallStatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color,
            letterSpacing = (-0.5).sp
        )

        Text(
            text = subtitle,
            fontSize = 10.sp,
            color = Color(0xFF6C757D),
            fontWeight = FontWeight.Medium
        )

        Text(
            text = title,
            fontSize = 11.sp,
            color = Color(0xFF0A3D62),
            fontWeight = FontWeight.Bold,
        )
    }
}

