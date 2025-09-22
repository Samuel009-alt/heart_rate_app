package com.example.heart_rate_app.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heart_rate_app.data.models.HeartRateReading


@Composable
fun HealthMetricsSection(readings: List<HeartRateReading>) {
    val averageHeartRate = if (readings.isNotEmpty()) {
        readings.map { it.bpm }.average().toInt()
    } else 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Icon(
                    Icons.Outlined.Analytics,
                    contentDescription = null,
                    tint = Color(0xFF3498DB),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Health Metrics",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2C3E50),
                    letterSpacing = (-0.25).sp
                )
            }

            HealthMetricItem(
                icon = Icons.Filled.Favorite,
                title = "Average Heart Rate",
                value = if (averageHeartRate > 0) "$averageHeartRate BPM" else "-- BPM",
                subtitle = "Normal range: 60-100 BPM",
                color = Color(0xFFE74C3C)
            )

            Spacer(modifier = Modifier.height(20.dp))

            HealthMetricItem(
                icon = Icons.Outlined.Assessment,
                title = "Total Measurements",
                value = "${readings.size} readings",
                subtitle = "Keep tracking daily",
                color = Color(0xFF27AE60)
            )

            Spacer(modifier = Modifier.height(20.dp))

            HealthMetricItem(
                icon = Icons.Outlined.TrackChanges,
                title = "Health Goal",
                value = "Stay Healthy",
                subtitle = "Monitor regularly",
                color = Color(0xFF3498DB)
            )
        }
    }
}

@Composable
fun HealthMetricItem(
    icon: ImageVector,
    title: String,
    value: String,
    subtitle: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C3E50)
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
