package com.example.heart_rate_app.screens.dashboard.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// LESSON: Enhanced Heart Rate Circle with Smooth Animations
@Composable
fun HeartRateCircle(
    heartRate: Int,
    progress: Float,
    isMeasuring: Boolean
) {
    // LESSON: Smooth progress animation
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 300),
        label = "progress_animation"
    )

    // LESSON: Heart beat animation
    val heartScale by animateFloatAsState(
        targetValue = if (isMeasuring && heartRate > 0) 1.1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_beat"
    )

    Card(
        modifier = Modifier.size(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(100.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // LESSON: Progress ring canvas drawing
            Canvas(modifier = Modifier.size(180.dp)) {
                // Background circle
                drawCircle(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    radius = size.minDimension / 2,
                    style = Stroke(width = 12.dp.toPx())
                )

                // Progress arc (only when measuring)
                if (isMeasuring && animatedProgress > 0f) {
                    drawArc(
                        color = Color(0xFF2196F3),
                        startAngle = -90f,
                        sweepAngle = 360f * animatedProgress,
                        useCenter = false,
                        style = Stroke(
                            width = 12.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
            }

            // LESSON: Heart rate display content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Heart icon with beat animation
                Text(
                    text = "❤️",
                    fontSize = (24 * heartScale).sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = if (heartRate > 0) "$heartRate" else "--",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isMeasuring) Color(0xFF2196F3) else Color.Gray
                )
                Text(
                    text = "BPM",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                // Progress percentage
                if (isMeasuring && progress > 0f) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        fontSize = 12.sp,
                        color = Color(0xFF2196F3),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
