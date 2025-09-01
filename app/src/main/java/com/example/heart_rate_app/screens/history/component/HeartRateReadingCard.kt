package com.example.heart_rate_app.screens.history.component

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.heart_rate_app.data.models.HeartRateReading

@Composable
fun HeartRateReadingCard(
    reading: HeartRateReading,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${reading.bpm} BPM", // Fixed: using reading.bpm
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = reading.date, // Fixed: using reading.date
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
