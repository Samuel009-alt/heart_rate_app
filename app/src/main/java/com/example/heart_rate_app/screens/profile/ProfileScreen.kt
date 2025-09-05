package com.example.heart_rate_app.screens.profile

import android.R.id.message
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.heart_rate_app.screens.profile.components.EditActionButtons
import com.example.heart_rate_app.screens.profile.components.LoadingIndicator
import com.example.heart_rate_app.screens.profile.components.PersonalInfoSection
import com.example.heart_rate_app.screens.profile.components.ProfileHeaderSection
import com.example.heart_rate_app.screens.profile.components.SuccessDialog
import com.example.heart_rate_app.viewmodel.AuthViewModel
import com.google.firebase.ktx.BuildConfig
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    val userState by authViewModel.currentUser.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

    // State variables for editing
    var fullName by remember { mutableStateOf(userState?.fullName ?: "") }
    var age by remember { mutableStateOf(userState?.age?.toString() ?: "") }
    var gender by remember { mutableStateOf(userState?.gender ?: "") }
    var phoneNumber by remember { mutableStateOf(userState?.phoneNumber ?: "") }
    var address by remember { mutableStateOf(userState?.address ?: "") }
    var isEditing by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }


    // Update local state whenever user data changes
    LaunchedEffect(userState) {
        userState?.let { user ->
            fullName = user.fullName ?: ""
            age = user.age?.toString() ?: ""
            gender = user.gender ?: ""
            phoneNumber = user.phoneNumber ?: ""
            address = user.address ?: ""
        }
    }

    //  Handle navigate after success
    LaunchedEffect(Unit) {
        if (showSuccessDialog){
            delay(1500)
            navController.navigate("profile_confirmation")
            isEditing = false
            showSuccessDialog = false
        }
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Profile",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    // BACK BUTTON ADDED HERE
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    if (!isEditing) {
                        IconButton(
                            onClick = { isEditing = true },
                            modifier = Modifier
                                .padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ){ padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ){
            if (isLoading){
                LoadingIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Profile Header with Avatar
                    item {
                        ProfileHeaderSection(
                            userState = userState,
                            isEditing = isEditing
                        )
                    }

                    // Personal Information Section
                    item {
                        PersonalInfoSection(
                            fullName = fullName,
                            email = userState?.email ?: "",
                            age = age,
                            gender = gender,
                            phoneNumber = phoneNumber,
                            address = address,
                            isEditing = isEditing,
                            onNameChange = { fullName = it },
                            onAgeChange = { age = it },
                            onGenderChange = { gender = it },
                            onPhoneChange = { phoneNumber = it },
                            onAddressChange = { address = it }
                        )
                    }

                    // Action Buttons
                    if (isEditing) {
                        item {
                            EditActionButtons(
                                onSave = {
                                    val updatedUser = userState?.copy(
                                        fullName = fullName.trim(),
                                        age = if (age.isNotEmpty()) age.toIntOrNull() else null,
                                        gender = if (gender.isNotEmpty()) gender.trim() else null,
                                        phoneNumber = if (phoneNumber.isNotEmpty()) phoneNumber.trim() else null,
                                        address = if (address.isNotEmpty()) address.trim() else null
                                    )

                                    if (updatedUser != null) {
                                        authViewModel.updateUserProfile(updatedUser) { success ->

                                            // Handle success or failure
                                            if (success) {
                                                showSuccessDialog = true
                                            }
                                        }
                                    }
                                },
                                onCancel = {
                                    // Reset to original values
                                    userState?.let { user ->
                                        fullName = user.fullName ?: ""
                                        age = user.age?.toString() ?: ""
                                        gender = user.gender ?: ""
                                        phoneNumber = user.phoneNumber ?: ""
                                        address = user.address ?: ""
                                    }
                                    isEditing = false
                                }
                            )
                        }
                    }
                }
            }
            // Success Dialog
            if (showSuccessDialog) {
                SuccessDialog {
                    showSuccessDialog = false
                }
            }
        }
    }
}

