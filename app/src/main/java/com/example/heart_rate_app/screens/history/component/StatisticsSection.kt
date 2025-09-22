package com.example.heart_rate_app.screens.history.component

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heart_rate_app.data.models.HeartRateReading
import com.example.heart_rate_app.ui.theme.SoftBlack
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsSection(readings: List<HeartRateReading>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Consistent header styling
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 5.dp)
        ) {
            Icon(
                Icons.Outlined.BarChart,
                contentDescription = null,
                tint = SoftBlack,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "HEART RATE STATISTICS",
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SoftBlack,
            )
        }

        if (readings.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Outlined.Analytics,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No data available",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Take some heart rate measurements to see statistics",
                    fontSize = 14.sp,
                    color = Color.Gray.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Calculate daily averages for the last 7 days
            val dailyAverages = calculateDailyAverages(readings)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Time period selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Last 7 Days",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "${readings.size} readings",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Chart visualization
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        dailyAverages.forEachIndexed { index, average ->
                            val maxHeight = 60.dp
                            val minHeight = 8.dp
                            val normalizedHeight = if (average > 0) {
                                val heightFactor = (average / 120.0).coerceIn(0.0, 1.0).toFloat()
                                minHeight + (maxHeight - minHeight) * heightFactor
                            } else minHeight

                            val isToday = index == dailyAverages.size - 1
                            val hasData = average > 0

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Value label
                                if (hasData) {
                                    Text(
                                        text = "${average.toInt()}",
                                        fontSize = 10.sp,
                                        color = if (isToday) Color(0xFF3498DB) else Color(0xFF6C757D),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                // Bar
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = when {
                                            !hasData -> Color(0xFFE9ECEF)
                                            isToday -> Color(0xFF3498DB)
                                            average >= 100 -> Color(0xFFE74C3C)
                                            average >= 60 -> Color(0xFF27AE60)
                                            else -> Color(0xFFFF9800)
                                        }
                                    ),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier
                                        .width(12.dp)
                                        .height(normalizedHeight)
                                ) {}
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Day labels
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val dayLabels = getDayLabels()
                        dayLabels.forEach { day ->
                            Text(
                                text = day,
                                fontSize = 12.sp,
                                color = Color(0xFF6C757D),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LegendItem(
                            color = Color(0xFF27AE60)
                            , label = "Normal"
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                        LegendItem(
                            color = Color(0xFFFF9800)
                            , label = "Low"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        LegendItem(
                            color = Color(0xFFE74C3C),
                            label = "High"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = SoftBlack,
            fontWeight = FontWeight.Bold
        )
    }
}

// Helper functions
private fun calculateDailyAverages(readings: List<HeartRateReading>): List<Double> {
    val calendar = Calendar.getInstance()
    val dailyAverages = mutableListOf<Double>()

    // Get last 7 days
    for (i in 6 downTo 0) {
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, -i)

        val dayStart = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val dayEnd = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        val dayReadings = readings.filter { reading ->
            reading.timestamp >= dayStart && reading.timestamp <= dayEnd
        }

        val average = if (dayReadings.isNotEmpty()) {
            dayReadings.map { it.bpm }.average()
        } else 0.0

        dailyAverages.add(average)
    }

    return dailyAverages
}

private fun getDayLabels(): List<String> {
    val calendar = Calendar.getInstance()
    val labels = mutableListOf<String>()
    val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())

    for (i in 6 downTo 0) {
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, -i)
        labels.add(dayFormatter.format(calendar.time))
    }

    return labels
}