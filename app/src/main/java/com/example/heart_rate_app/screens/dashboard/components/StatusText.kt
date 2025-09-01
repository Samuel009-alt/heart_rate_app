package com.example.heart_rate_app.screens.dashboard.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// LESSON: Dynamic Status Text Component
@Composable
fun StatusText(
    isMeasuring: Boolean,
    isLoading: Boolean,
    progress: Float
) {
    val statusText = when {
        isLoading -> "Saving measurement..."
        isMeasuring -> "Measuring... ${(progress * 100).toInt()}%"
        progress == 1f && !isMeasuring -> "Measurement saved successfully!"
        else -> "Place your finger on the camera and press start"
    }

    val statusColor = when {
        isLoading -> Color(0xFFFF9800) // Orange
        isMeasuring -> Color(0xFF2196F3) // Blue
        progress == 1f && !isMeasuring -> Color(0xFF4CAF50) // Green
        else -> Color.Gray
    }

    Text(
        text = statusText,
        fontSize = 14.sp,
        color = statusColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}