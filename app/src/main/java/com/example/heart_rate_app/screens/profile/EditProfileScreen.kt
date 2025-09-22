package com.example.heart_rate_app.screens.profile

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.ui.theme.YellowPrimary
import com.example.heart_rate_app.viewmodel.AuthViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.example.heart_rate_app.data.cloudinaryConfig.ImageUploadHelper
import com.example.heart_rate_app.ui.theme.GrayText
import com.example.heart_rate_app.ui.theme.LightBlue
import com.example.heart_rate_app.ui.theme.SoftBlack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    var fullName by remember { mutableStateOf(currentUser?.fullName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var phoneNumber by remember { mutableStateOf(currentUser?.phoneNumber ?: "") }
    var address by remember { mutableStateOf(currentUser?.address ?: "") }
    var age by remember { mutableStateOf(currentUser?.age?.toString() ?: "") }
    var gender by remember { mutableStateOf(currentUser?.gender ?: "") }
    var profileImageUrl by remember { mutableStateOf(currentUser?.profileImageUrl ?: "") }
    var isUploading by remember { mutableStateOf(false) }
    var uploadError by remember { mutableStateOf<String?>(null) }

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

    var showGenderDropdown by remember { mutableStateOf(false) }
    val genderOptions = listOf("Male", "Female", "Other", "Prefer not to say")

    // Image picker launcher with improved error handling
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { selectedUri ->
            isUploading = true
            uploadError = null

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val uploadHelper = ImageUploadHelper()
                    val uploadedUrl = uploadHelper.uploadToCloudinary(selectedUri, context)

                    if (uploadedUrl != null) {
                        profileImageUrl = uploadedUrl
                        Toast.makeText(context, "Image uploaded successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        uploadError = "Upload failed. Please check your internet connection and try again."
                        Toast.makeText(context, uploadError, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    uploadError = "Upload error: ${e.localizedMessage}"
                    Toast.makeText(context, uploadError, Toast.LENGTH_LONG).show()
                } finally {
                    isUploading = false
                }
            }
        }
    }

    // Update fields when user data loads
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            fullName = user.fullName ?: ""
            email = user.email ?: ""
            phoneNumber = user.phoneNumber ?: ""
            address = user.address ?: ""
            age = user.age?.toString() ?: ""
            gender = user.gender ?: ""
            profileImageUrl = user.profileImageUrl ?: ""
        }
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
                    text = "Edit Profile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50),
                )

                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Profile Picture Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(5.dp)
            ) {
                Box {
                    if (profileImageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = profileImageUrl,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(
                                    3.dp,
                                    if (isUploading) YellowPrimary else Color.Transparent,
                                    CircleShape
                                ),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    LightBlue,
                                    CircleShape
                                )
                                .border(
                                    3.dp,
                                    if (isUploading) YellowPrimary else Color.Transparent,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                contentDescription = "Add Photo",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    // Camera button with loading state
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                if (isUploading) Color.Gray else YellowPrimary,
                                CircleShape
                            )
                            .align(Alignment.BottomEnd)
                            .clickable(enabled = !isUploading) {
                                imagePickerLauncher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isUploading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(12.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                contentDescription = "Change Photo",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Status text with improved messaging
                Text(
                    text = when {
                        isUploading -> "Uploading image..."
                        uploadError != null -> "Upload failed - Tap to retry"
                        profileImageUrl.isNotEmpty() -> "Tap camera to change"
                        else -> "Tap camera to add photo"
                    },
                    fontSize = 12.sp,
                    color = when {
                        isUploading -> YellowPrimary
                        uploadError != null -> Color.Red
                        else -> GrayText
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            // Personal Information Section
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
                        text = "UPDATE PROFILE",
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
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = SoftBlack,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PERSONAL INFORMATION",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SoftBlack,
                    )
                }

                Text(
                    text = "Please fill in the required information below to update your profile.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 35.dp)
                )

                // Form fields with consistent styling
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Full Name Field
                    UnderlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "Full Name",
                        icon = Icons.Outlined.Person
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Email Field
                    UnderlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        icon = Icons.Outlined.Email,
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Phone Number Field
                    UnderlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = "Phone Number",
                        icon = Icons.Outlined.Phone,
                        keyboardType = KeyboardType.Phone
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Row for Gender and Age (as requested)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Gender Dropdown
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
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { showGenderDropdown = true }
                                    ) {
                                        Text(
                                            text = if (gender.isNotEmpty()) gender else "Select gender",
                                            color = if (gender.isNotEmpty()) SoftBlack else Color.Gray,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )

                                        DropdownMenu(
                                            expanded = showGenderDropdown,
                                            onDismissRequest = { showGenderDropdown = false }
                                        ) {
                                            genderOptions.forEach { option ->
                                                DropdownMenuItem(
                                                    text = { Text(option) },
                                                    onClick = {
                                                        gender = option
                                                        showGenderDropdown = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            HorizontalDivider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp
                            )
                        }

                        // Age Field
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
                                    BasicTextField(
                                        value = age,
                                        onValueChange = {
                                            if (it.all { char -> char.isDigit() } && it.length <= 3) {
                                                age = it
                                            }
                                        },
                                        textStyle = TextStyle(
                                            color = SoftBlack,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        decorationBox = { innerTextField ->
                                            Box(
                                                modifier = Modifier.padding(bottom = 13.dp)
                                            ) {
                                                if (age.isEmpty()) {
                                                    Text(
                                                        text = "Enter age",
                                                        color = Color.Gray,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                }
                                                innerTextField()
                                            }
                                        }
                                    )
                                }
                            }
                            HorizontalDivider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Address Field
                    UnderlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Address",
                        icon = Icons.Outlined.LocationOn,
                        isLast = true
                    )

                    Spacer(modifier = Modifier.height(35.dp))
                }

                // Save button with improved loading state
                Button(
                    onClick = {
                        currentUser?.let { user ->
                            val updatedUser = user.copy(
                                fullName = fullName.trim(),
                                email = email.trim(),
                                phoneNumber = phoneNumber.trim().takeIf { it.isNotEmpty() },
                                address = address.trim().takeIf { it.isNotEmpty() },
                                age = age.trim().toIntOrNull(),
                                gender = gender.takeIf { it.isNotEmpty() },
                                profileImageUrl = profileImageUrl.takeIf { it.isNotEmpty() }
                            )

                            authViewModel.updateUserProfile(updatedUser) { success ->
                                if (success) {
                                    navController.navigate( "profile_confirmation" )
                                } else {
                                    Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    enabled = fullName.isNotBlank() && email.isNotBlank() && !isLoading && !isUploading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (fullName.isNotBlank() && email.isNotBlank() && !isLoading && !isUploading) {
                            SoftBlack
                        } else {
                            Color.Gray
                        },
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    if (isLoading || isUploading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (isUploading) "Uploading..." else "Saving...",
                            fontWeight = FontWeight.SemiBold
                        )
                    } else {
                        Icon(
                            Icons.Outlined.Save,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save Changes",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun UnderlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
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
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = SoftBlack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = "Enter $label",
                                    color = Color.Gray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        if (!isLast) {
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
        }
    }
}