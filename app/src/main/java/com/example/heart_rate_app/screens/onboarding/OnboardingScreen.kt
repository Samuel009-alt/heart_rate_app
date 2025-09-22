package com.example.heart_rate_app.screens.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material.icons.outlined.WavingHand
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.heart_rate_app.R
import com.example.heart_rate_app.screens.onboarding.components.FeatureHighlight
import com.example.heart_rate_app.ui.theme.SoftBlack
import com.example.heart_rate_app.ui.theme.YellowPrimary

@Composable
fun OnboardingScreen(
    navController: NavController
) {
    // Animation states
    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    val rotationAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 360f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FA),
                        Color(0xFFE9ECEF)
                    )
                )
            )
    ) {

        // Enhanced decorative circles with gradients and animations
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 280.dp, y = (-60).dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            YellowPrimary.copy(alpha = 0.8f),
                            YellowPrimary.copy(alpha = 0.4f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.8f
                    scaleY = scaleAnimation * 0.8f
                    rotationZ = rotationAnimation * 0.3f
                }
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (-30).dp, y = 100.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF3498DB).copy(alpha = 0.6f),
                            Color(0xFF3498DB).copy(alpha = 0.2f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.9f
                    scaleY = scaleAnimation * 0.9f
                    rotationZ = -rotationAnimation * 0.5f
                }
        )

        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(x = (-40).dp, y = 580.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            YellowPrimary.copy(alpha = 0.7f),
                            YellowPrimary.copy(alpha = 0.3f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.7f
                    scaleY = scaleAnimation * 0.7f
                    rotationZ = rotationAnimation * 0.4f
                }
        )

        Box(
            modifier = Modifier
                .size(90.dp)
                .offset(x = 320.dp, y = 450.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE74C3C).copy(alpha = 0.5f),
                            Color(0xFFE74C3C).copy(alpha = 0.2f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation
                    scaleY = scaleAnimation
                    rotationZ = -rotationAnimation * 0.6f
                }
        )

        Box(
            modifier = Modifier
                .size(110.dp)
                .offset(x = 250.dp, y = 650.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF27AE60).copy(alpha = 0.4f),
                            Color(0xFF27AE60).copy(alpha = 0.1f)
                        )
                    )
                )
                .graphicsLayer {
                    scaleX = scaleAnimation * 0.6f
                    scaleY = scaleAnimation * 0.6f
                    rotationZ = rotationAnimation * 0.7f
                }
        )

        // Main content card with compact design
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
                .align(Alignment.Center)
                .shadow(8.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_heart_main_logo),
                        contentDescription = "Heart Logo",
                        modifier = Modifier
                            .size(48.dp),
                        )

                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "HR.",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SoftBlack,
                        letterSpacing = (-1).sp
                    )
                }

                Text(
                    text = "H E A R T R A T E  M O N I T O R",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF6C757D),
                    letterSpacing = 2.sp,
                    modifier = Modifier
                        .padding(bottom = 28.dp)
                )

                // Enhanced welcome section
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        YellowPrimary,
                                        Color(0xFFFFB366)
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                                ),
                                RoundedCornerShape(20.dp)
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                            ) {
                                Icon(
                                    Icons.Outlined.WavingHand,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "WELCOME",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    letterSpacing = 1.sp
                                )
                            }

                            Text(
                                text = "Track your heart rate and monitor your health with real-time data, detailed analytics, and comprehensive historical trends.",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.95f),
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(bottom = 24.dp)
                            )

                            // Enhanced button section
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Primary Sign In button
                                Button(
                                    onClick = {
                                        navController.navigate("sign_in") {
                                            popUpTo("onboarding") { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White,
                                        contentColor = SoftBlack
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 4.dp,
                                        pressedElevation = 2.dp
                                    )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Outlined.Login,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Sign In",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }

                                // Secondary Sign Up button
                                OutlinedButton(
                                    onClick = {
                                        navController.navigate("sign_up")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.White
                                    ),
                                    border = BorderStroke(2.dp, Color.White.copy(alpha = 0.8f)),
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Outlined.PersonAdd,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Create Account",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Feature highlights
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureHighlight(
                        icon = Icons.Outlined.Timeline,
                        text = "Real-time\nTracking",
                        color = Color(0xFF3498DB)
                    )
                    FeatureHighlight(
                        icon = Icons.Outlined.Analytics,
                        text = "Health\nInsights",
                        color = Color(0xFF27AE60)
                    )
                    FeatureHighlight(
                        icon = Icons.Outlined.History,
                        text = "Historical\nData",
                        color = Color(0xFFE74C3C)
                    )
                }
            }
        }
    }
}