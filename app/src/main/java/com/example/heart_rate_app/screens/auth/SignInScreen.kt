package com.example.heart_rate_app.screens.auth

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.R
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.ui.theme.PureWhite
import com.example.heart_rate_app.ui.theme.SoftBlack
import com.example.heart_rate_app.ui.theme.YellowPrimary
import com.example.heart_rate_app.viewmodel.AuthViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
){
    var errorMessage by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Top navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF2C3E50)
                    )
                }
                TextButton(onClick = { navController.navigate(Routes.SIGN_UP) }) {
                    Text(
                        text = "Register",
                        color = Color(0xFF2C3E50),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Main content card
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .align(Alignment.CenterHorizontally)
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
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
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
                            .padding(bottom = 24.dp)
                    )

                    // Enhanced Create Account section
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
                                        end = Offset(
                                            Float.POSITIVE_INFINITY,
                                            Float.POSITIVE_INFINITY
                                        )
                                    ),
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(20.dp)
                        ) {
                            Column {
                                // Sign In Title
                                Text(
                                    text = "WELCOME BACK!",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,

                                    )

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Track your heart rate and monitor your health with real-time data, detailed analytics, and comprehensive historical trends.",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.95f),
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .padding(0.dp)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    placeholder = {
                                        Text(
                                            "Email Address",
                                            color = Color.White.copy(alpha = 0.7f),
                                            fontSize = 14.sp
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.Email,
                                            contentDescription = null,
                                            tint = Color.White.copy(alpha = 0.8f),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(30.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.White.copy(alpha = 0.8f),
                                        focusedTextColor = Color.White,
                                        cursorColor = Color.White,
                                        unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                                        unfocusedTextColor = Color.White
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    singleLine = true,
                                    textStyle = TextStyle(fontSize = 14.sp)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    placeholder = {
                                        Text(
                                            "Password",
                                            color = Color.White.copy(alpha = 0.7f),
                                            fontSize = 14.sp
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.Lock,
                                            contentDescription = null,
                                            tint = Color.White.copy(alpha = 0.8f),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                            Icon(
                                                if (passwordVisible)
                                                    Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                                tint = Color.White.copy(alpha = 0.8f),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    },
                                    visualTransformation = if (passwordVisible)
                                        VisualTransformation.None else PasswordVisualTransformation(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(30.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.White.copy(alpha = 0.8f),
                                        focusedTextColor = Color.White,
                                        cursorColor = Color.White,
                                        unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                                        unfocusedTextColor = Color.White
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    singleLine = true,
                                    textStyle = TextStyle(fontSize = 14.sp)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Error message
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color(0xFFE53935),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Enhanced button section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Sign in button
                        Button(
                            onClick = {
                                when {
                                    email.isBlank() || password.isBlank() -> {
                                        errorMessage = "All fields are required"
                                        return@Button
                                    }
                                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                        errorMessage = "Please enter a valid email"
                                        return@Button
                                    }
                                    password.length < 6 -> {
                                        errorMessage = "Password must be at least 6 characters"
                                        return@Button
                                    }
                                    else -> {
                                        errorMessage = ""
                                        authViewModel.signIn(email, password,
                                            onSuccess = {
                                                navController.navigate(Routes.DASHBOARD) {
                                                    popUpTo(Routes.SIGN_IN) { inclusive = true }
                                                }
                                            },
                                            onError = { error ->
                                                errorMessage = error
                                            }
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SoftBlack,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(24.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 2.dp
                            )
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(20.dp),
                                    color = Color.Black,
                                    strokeWidth = 2.dp
                                )
                            } else {
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
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}