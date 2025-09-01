package com.example.heart_rate_app.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heart_rate_app.viewmodel.AuthViewModel
import com.google.firebase.ktx.BuildConfig
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = viewModel(),
    onSaveSuccess: () -> Unit
) {
    val userState by authViewModel.currentUser.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

    var fullName by remember { mutableStateOf(userState?.fullName ?: "") }
    var age by remember { mutableStateOf(userState?.age?.toString() ?: "") }
    var gender by remember { mutableStateOf(userState?.gender ?: "") }
    val email = userState?.email ?: ""

    var message by remember { mutableStateOf<String?>(null) }
    var isEditing by remember { mutableStateOf(false) }

    // FIXED: Update local state whenever user data changes
    LaunchedEffect(userState) {
        userState?.let { user ->
            fullName = user.fullName ?: ""
            age = user.age?.toString() ?: ""
            gender = user.gender ?: ""
        }
    }

    // FIXED: Clear message after some time
    LaunchedEffect(message) {
        if (message != null) {
            delay(3000) // Show message for 3 seconds
            message = null
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ){ padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ){
            if (isLoading){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading profile...")
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()), // ADDED: Make scrollable
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Profile header
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Profile Information",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Email: $email",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // FIXED: Full Name field - always editable when in editing mode
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("Full Name") },
                            placeholder = { Text("Enter your full name") },
                            singleLine = true,
                            enabled = isEditing, // Only editable in editing mode
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Full Name"
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // FIXED: Age field - always editable when in editing mode
                        OutlinedTextField(
                            value = age,
                            onValueChange = { newValue ->
                                // Only allow digits and limit to reasonable age range
                                val filteredValue = newValue.filter { it.isDigit() }
                                if (filteredValue.isEmpty() || (filteredValue.toIntOrNull()?.let { it <= 120 } == true)) {
                                    age = filteredValue
                                }
                            },
                            label = { Text("Age") },
                            placeholder = { Text("Enter your age") },
                            singleLine = true,
                            enabled = isEditing, // FIXED: Now properly controlled by isEditing
                            leadingIcon = {
                                Icon(
                                    Icons.Default.DateRange,
                                    contentDescription = "Age"
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // FIXED: Gender field - always editable when in editing mode
                        OutlinedTextField(
                            value = gender,
                            onValueChange = { gender = it },
                            label = { Text("Gender") },
                            placeholder = { Text("Enter your gender") },
                            singleLine = true,
                            enabled = isEditing, // Only editable in editing mode
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Face,
                                    contentDescription = "Gender"
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Action buttons
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            if (isEditing){
                                // Save button
                                Button(
                                    onClick = {
                                        // FIXED: Improved validation
                                        when {
                                            fullName.isBlank() -> {
                                                message = "Full name cannot be empty"
                                            }
                                            fullName.length < 2 -> {
                                                message = "Full name must be at least 2 characters"
                                            }
                                            age.isNotEmpty() && (age.toIntOrNull() == null || age.toInt() < 1) -> {
                                                message = "Please enter a valid age"
                                            }
                                            else -> {

                                                val updatedUser = userState?.copy(
                                                    fullName = fullName.trim(),
                                                    age = if (age.isNotEmpty()) age.toIntOrNull() else null,
                                                    gender = if (gender.isNotEmpty()) gender.trim() else null
                                                )

                                                if (updatedUser != null) {
                                                    authViewModel.updateUserProfile(updatedUser) { success ->
                                                        if (success) {
                                                            message = "Profile updated successfully!"
                                                            isEditing = false
                                                            onSaveSuccess()
                                                        } else {
                                                            message = "Failed to update profile. Please try again."
                                                        }
                                                    }
                                                } else {
                                                    message = "Unable to update profile. Please try again."
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Save")
                                }

                                // Cancel button
                                OutlinedButton(
                                    onClick = {
                                        // Reset fields to original values
                                        userState?.let { user ->
                                            fullName = user.fullName ?: ""
                                            age = user.age?.toString() ?: ""
                                            gender = user.gender ?: ""
                                        }
                                        isEditing = false
                                        message = null
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Cancel")
                                }
                            } else {
                                // Edit button
                                Button(
                                    onClick = {
                                        isEditing = true
                                        message = null
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Edit Profile")
                                }
                            }
                        }
                    }

                    message?.let { msg ->
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = if (msg.contains("success")) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.errorContainer
                                }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (msg.contains("success")) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onErrorContainer
                                },
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Show current data for debugging (remove in production)
                    if (BuildConfig.DEBUG) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    "Debug Info:",
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(
                                    "User ID: ${userState?.uid ?: "null"}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    "Current Data: ${userState.toString()}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

