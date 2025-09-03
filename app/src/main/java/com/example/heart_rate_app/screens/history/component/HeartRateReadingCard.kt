package com.example.heart_rate_app.screens.history.component

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heart_rate_app.data.models.HeartRateReading
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HeartRateReadingCard(
    reading: HeartRateReading,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Heart rate value
            Text(
                text = "${reading.bpm} BPM",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Timestamp - formatted nicely
            val date = Date(reading.timestamp)
            val formatter = SimpleDateFormat(
                "MMM dd, hh:mm a",
                Locale.getDefault()
            )
            Text(
                text = formatter.format(date),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
