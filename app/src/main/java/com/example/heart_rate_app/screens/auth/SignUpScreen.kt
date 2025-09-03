package com.example.heart_rate_app.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.ui.theme.Heart_Rate_AppTheme
import com.example.heart_rate_app.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    var errorMessage by remember { mutableStateOf("") }
   //  Observe loading state from ViewModel
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SIGN UP BUTTON
        Button(
            onClick = {
                when {
                    fullName.isBlank() || email.isBlank() || password.isBlank() -> {
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
                        authViewModel.signUp(fullName, email, password,
                            onSuccess = {
                                navController.navigate(Routes.DASHBOARD) {
                                    popUpTo(Routes.SIGN_UP) { inclusive = true }
                                }
                            },
                            onError = { error ->
                                errorMessage = error
                            }
                        )
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Creating Account..." else "Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate(Routes.SIGN_IN) }) {
            Text("Already have an account? Sign In")
        }
    }
}
//// Colors
//val accentColor = Color(0xFF1E3A8A) // Dark blue
//val placeholderColor = Color(0xFF9CA3AF)
//val textColor = Color(0xFF000000)
//
//// Custom font
////    val montserrat = FontFamily(Font(R.font.montserrat_regular))

//Box(
//  modifier = Modifier
//      .fillMaxSize()
//      .background(
//          brush = Brush.verticalGradient(
//colors = listOf(Color(0xFFFDFDFD), Color(0xFFF0F0F0))
//)
//)
//.padding(horizontal = 24.dp),
//contentAlignment = Alignment.Center
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        // Logo with heart
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(
//                text = "HR",
//                style = MaterialTheme.typography.headlineLarge.copy(
////                        fontFamily = montserrat,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 48.sp,
//                    color = textColor
//                )
//            )
//            Spacer(modifier = Modifier.width(8.dp))
////                Icon(
////                    painter = painterResource(id = R.drawable.ic_heart), // Add heart icon to drawable
////                    contentDescription = "Heart",
////                    tint = accentColor,
////                    modifier = Modifier.size(32.dp)
////                )
//        }
//
//        Spacer(modifier = Modifier.height(40.dp))
//
//        // Name Field
//        OutlinedTextField(
//            value = firstName,
//            onValueChange = { firstName = it },
//            placeholder = { Text("Name", color = placeholderColor) },
//            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name", tint = placeholderColor) },
//            shape = RoundedCornerShape(50),
//            singleLine = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent,
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            )
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Email Field
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            placeholder = { Text("Email", color = placeholderColor) },
//            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email", tint = placeholderColor) },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//            shape = RoundedCornerShape(50),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent,
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            )
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Password Field
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            placeholder = { Text("Password", color = placeholderColor) },
//            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password", tint = placeholderColor) },
//            singleLine = true,
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            shape = RoundedCornerShape(50),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent,
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            )
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Sign Up Button
//        Button(
//            onClick = { /* Connect Firebase here later */ },
//            colors = ButtonDefaults.buttonColors(containerColor = accentColor),
//            shape = RoundedCornerShape(50),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//        ) {
//            Text("Sign Up", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Already have account? Sign In
//        Text(
//            buildAnnotatedString {
//                append("Already have an account? ")
//                withStyle(style = SpanStyle(color = accentColor, fontWeight = FontWeight.Bold)) {
//                    append("Sign In")
//                }
//            },
//            modifier = Modifier.clickable { navController.navigate("signin") },
//            fontSize = 14.sp,
//            color = Color.Gray
//        )
//    }
//}

