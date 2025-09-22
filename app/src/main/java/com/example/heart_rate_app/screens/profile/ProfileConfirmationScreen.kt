package com.example.heart_rate_app.screens.profile

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.heart_rate_app.ui.theme.GrayText
import com.example.heart_rate_app.ui.theme.LightBlue
import com.example.heart_rate_app.ui.theme.SoftBlack
import com.example.heart_rate_app.ui.theme.YellowPrimary
import com.example.heart_rate_app.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileConfirmationScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isConfirming by remember { mutableStateOf(false) }

    // Animation states
    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "scale"
    )
    val rotationAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 360f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing),
        label = "rotation"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Header with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF2C3E50),
                    )
                }

                Text(
                    text = "Confirm Changes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50),
                )

                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Profile Preview Section
            Column(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture Preview with border animation
                Box {
                    if (!currentUser?.profileImageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = currentUser?.profileImageUrl,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(
                                    3.dp,
                                    Brush.linearGradient(
                                        colors = listOf(YellowPrimary, Color(0xFF3498DB))
                                    ),
                                    CircleShape
                                ),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(LightBlue, Color(0xFF3498DB))
                                    ),
                                    CircleShape
                                )
                                .border(
                                    3.dp,
                                    Brush.linearGradient(
                                        colors = listOf(YellowPrimary, Color(0xFF3498DB))
                                    ),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentUser?.fullName?.firstOrNull()?.toString()?.uppercase() ?: "U",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }
                    }

                    // Success checkmark overlay
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFF4CAF50), CircleShape)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Verified",
                            tint = White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentUser?.fullName ?: "User",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2C3E50),
                    letterSpacing = (-0.5).sp
                )

                Text(
                    text = currentUser?.email ?: "user@example.com",
                    fontSize = 16.sp,
                    color = Color(0xFF6C757D),
                    fontWeight = FontWeight.Medium
                )
            }

            // Confirmation message
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "CONFIRM CHANGES",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        letterSpacing = 2.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = SoftBlack,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "REVIEW YOUR CHANGES",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SoftBlack,
                    )
                }

                Text(
                    text = "Please review your updated profile information below. Once confirmed, your changes will be saved to your account.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Full Name Field
                    ProfileInfoItem(
                        label = "Full Name",
                        value = currentUser?.fullName ?: "",
                        icon = Icons.Outlined.Person
                    )

                    // Email Field
                    ProfileInfoItem(
                        label = "Email",
                        value = currentUser?.email ?: "",
                        icon = Icons.Outlined.Email
                    )

                    // Phone Number Field
                    if (!currentUser?.phoneNumber.isNullOrEmpty()) {
                        ProfileInfoItem(
                            label = "Phone Number",
                            value = currentUser?.phoneNumber ?: "",
                            icon = Icons.Outlined.Phone
                        )
                    }

                    // Row for Gender and Age
                    if (!currentUser?.gender.isNullOrEmpty() || currentUser?.age != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Gender Field
                            if (!currentUser?.gender.isNullOrEmpty()) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Outlined.Wc,
                                            contentDescription = null,
                                            tint = SoftBlack.copy(alpha = 0.7f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Gender",
                                                color = Color.Gray,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Text(
                                                text = currentUser?.gender ?: "",
                                                color = SoftBlack,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }

                                    if (currentUser?.address.isNullOrEmpty()) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                    } else {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        HorizontalDivider(
                                            color = Color.LightGray.copy(alpha = 0.5f),
                                            thickness = 1.dp
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }

                            // Age Field
                            if (currentUser?.age != null) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Outlined.Cake,
                                            contentDescription = null,
                                            tint = SoftBlack.copy(alpha = 0.7f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Age",
                                                color = Color.Gray,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Text(
                                                text = currentUser?.age?.toString() ?: "",
                                                color = SoftBlack,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }

                                    if (currentUser?.address.isNullOrEmpty()) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                    } else {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        HorizontalDivider(
                                            color = Color.LightGray.copy(alpha = 0.5f),
                                            thickness = 1.dp
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }

                    // Address Field
                    if (!currentUser?.address.isNullOrEmpty()) {
                        ProfileInfoItem(
                            label = "Address",
                            value = currentUser?.address ?: "",
                            icon = Icons.Outlined.LocationOn,
                            isLast = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons with enhanced styling
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Cancel Button
                    OutlinedButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SoftBlack
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Go Back",
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Confirm Button
                    Button(
                        onClick = {
                            isConfirming = true
                            // Simulate confirmation process
                            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                                kotlinx.coroutines.delay(1500) // Simulate processing time
                                isConfirming = false
                                showSuccessDialog = true
                            }
                        },
                        enabled = !isLoading && !isConfirming,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftBlack,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        if (isLoading || isConfirming) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = White
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Save,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when {
                                isConfirming -> "Confirming..."
                                isLoading -> "Saving..."
                                else -> "Confirm"
                            },
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Profile Updated!",
                        color = SoftBlack,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Text(
                    text = "Your profile has been successfully updated with the new information.",
                    color = GrayText,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("profile") {
                            popUpTo("edit_profile") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftBlack
                    )
                ) {
                    Text("View Profile", color = White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                    ),
                ) {
                    Text("Cancel", color = SoftBlack)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun ProfileInfoItem(
    label: String,
    value: String,
    icon: ImageVector,
    isLast: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = SoftBlack.copy(alpha = 0.7f),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = value,
                    color = SoftBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (!isLast) {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}