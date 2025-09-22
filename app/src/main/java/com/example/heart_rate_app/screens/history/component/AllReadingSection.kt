package com.example.heart_rate_app.screens.history.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heart_rate_app.data.models.HeartRateReading
import com.example.heart_rate_app.data.models.getCategory
import com.example.heart_rate_app.data.models.getFormattedDate
import com.example.heart_rate_app.data.models.getFormattedTime
import kotlin.collections.forEach

@Composable
fun AllReadingsSection(readings: List<HeartRateReading>) {
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
                    Icons.Outlined.History,
                    contentDescription = null,
                    tint = Color(0xFF3498DB),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "All Readings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2C3E50),
                    letterSpacing = (-0.25).sp
                )
            }

            if (readings.isEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF8F9FA)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Outlined.Timeline,
                                contentDescription = null,
                                tint = Color(0xFF6C757D),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No readings yet",
                                color = Color(0xFF6C757D),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Start monitoring to see your data here",
                                color = Color(0xFF95A5A6),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                val sortedReadings = readings.sortedByDescending { it.timestamp }

                sortedReadings.forEach { reading ->
                    HistoryItem(reading = reading)
                    if (reading != sortedReadings.last()) {
                        HorizontalDivider(
                            color = Color(0xFFF1F3F4),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(reading: HeartRateReading) {
    val category = reading.getCategory()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                tint = category.color,
                modifier = Modifier.size(20.dp)
            )

            Column {
                Text(
                    text = "${reading.bpm} BPM",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50),
                    letterSpacing = (-0.25).sp
                )
                Text(
                    text = category.label,
                    fontSize = 13.sp,
                    color = category.color,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = reading.getFormattedTime(),
                fontSize = 15.sp,
                color = Color(0xFF495057),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = reading.getFormattedDate(),
                fontSize = 12.sp,
                color = Color(0xFF6C757D),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

