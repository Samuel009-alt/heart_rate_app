package com.example.heart_rate_app.screens.profile.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.heart_rate_app.data.models.UserData

@Composable
fun ProfileInfoCard(
    user: UserData?,
    totalReadings: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Enhanced Profile Picture with Cloudinary support
        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(8.dp, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (!user?.profileImageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = user?.profileImageUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF3498DB),
                                    Color(0xFF2980B9)
                                )
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user?.fullName?.firstOrNull()?.toString()?.uppercase() ?: "U",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = (-1).sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = user?.fullName ?: "User",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2C3E50),
            letterSpacing = (-0.5).sp
        )

        Text(
            text = user?.email ?: "user@example.com",
            fontSize = 16.sp,
            color = Color(0xFF6C757D),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(28.dp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Heart Rate History",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2C3E50),
                    letterSpacing = (-0.25).sp
                )
            }

            // Enhanced Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStatItem(
                    title = "Readings",
                    value = totalReadings.toString(),
                    color = Color(0xFF27AE60),
                    icon = Icons.Outlined.Timeline
                )

                ProfileStatItem(
                    title = "Days Active",
                    value = "12",
                    color = Color(0xFF3498DB),
                    icon = Icons.Outlined.CalendarMonth
                )

                ProfileStatItem(
                    title = "Health Score",
                    value = "85",
                    color = Color(0xFFFF9800),
                    icon = Icons.Outlined.Star
                )
            }
        }
    }
}

@Composable
fun ProfileStatItem(
    title: String,
    value: String,
    color: Color,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color,
            letterSpacing = (-0.5).sp
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(0xFF6C757D),
            fontWeight = FontWeight.Medium
        )
    }
}

