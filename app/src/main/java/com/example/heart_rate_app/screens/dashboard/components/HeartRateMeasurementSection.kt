package com.example.heart_rate_app.screens.dashboard.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heart_rate_app.ui.theme.SoftBlack

@Composable
fun HeartRateMeasurementSection(
    currentHeartRate: Int,
    isMonitoring: Boolean,
    isLoading: Boolean,
    saveError: Boolean,
    onStartMonitoring: () -> Unit,
    onStopMonitoring: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = androidx.compose.animation.core.FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CURRENT HEART RATE",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SoftBlack,
            letterSpacing = (-0.5).sp,
            modifier = Modifier
                .padding(bottom = 28.dp)
        )

        Box(
            modifier = Modifier
                .size(260.dp),
            contentAlignment = Alignment.Center
        ) {
            // Outer gradient ring
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFFFF6B35),
                            Color(0xFFFF8E53),
                            Color(0xFFFF6B35)
                        )
                    ),
                    radius = size.minDimension / 2 - 10.dp.toPx(),
                    style = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Inner pulse effect
            if (isMonitoring) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = pulseScale
                            scaleY = pulseScale
                        }
                ) {
                    drawCircle(
                        color = Color(0xFFFF6B35).copy(alpha = 0.3f),
                        radius = size.minDimension / 2 - 30.dp.toPx(),
                        style = Stroke(width = 8.dp.toPx())
                    )
                }
            }

            // Center content with enhanced styling
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (isMonitoring) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Heart",
                    tint = if (isMonitoring) Color(0xFFE74C3C) else Color(0xFFFF6B35),
                    modifier = Modifier
                        .size(36.dp)
                        .graphicsLayer {
                            if (isMonitoring) {
                                scaleX = pulseScale
                                scaleY = pulseScale
                            }
                        }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (currentHeartRate > 0) "$currentHeartRate" else "--",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isMonitoring) Color(0xFFE74C3C) else Color(0xFF2196F3),
                    letterSpacing = (-2).sp
                )
                Text(
                    text = "BPM",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6C757D),
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Enhanced status message
        Box {
            Text(
                text = when {
                    saveError -> "Failed to save measurement. Try again."
                    isLoading -> "Saving measurement..."
                    isMonitoring -> "Measuring your heart rate..."
                    currentHeartRate > 0 -> "✓ Measurement complete!"
                    else -> "Ready to measure"
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = when {
                    saveError -> Color(0xFFDC3545)
                    isLoading -> Color(0xFFFF9800)
                    currentHeartRate > 0 -> Color(0xFF28A745)
                    else -> Color(0xFF6C757D)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Enhanced button
        Button(
            onClick = if (isMonitoring) onStopMonitoring else onStartMonitoring,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = when {
                    isLoading -> Color(0xFF95A5A6)
                    isMonitoring -> Color(0xFFE74C3C)
                    else -> Color(0xFF3498DB)
                }
            ),
            shape = RoundedCornerShape(29.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp,
                disabledElevation = 0.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                } else {
                    Icon(
                        imageVector = if (isMonitoring) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = when {
                        isLoading -> "Saving..."
                        isMonitoring -> "Stop Monitoring"
                        else -> "Start Monitoring"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
