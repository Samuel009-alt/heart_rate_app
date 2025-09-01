package com.example.heart_rate_app.screens.dashboard.components

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// LESSON: Measurement Button with Loading States
@Composable
fun MeasurementButton(
    isMeasuring: Boolean,
    isLoading: Boolean,
    onToggleMeasuring: () -> Unit
) {
    Button(
        onClick = onToggleMeasuring,
        enabled = !isLoading,
        modifier = Modifier
            .width(200.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isMeasuring) Color(0xFFE53935) else Color(0xFF4CAF50),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(
            text = when {
                isLoading -> "Saving..."
                isMeasuring -> "Stop Measuring"
                else -> "Start Measuring"
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
