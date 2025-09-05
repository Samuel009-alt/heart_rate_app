package com.example.heart_rate_app.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PersonalInfoSection (
    fullName: String,
    email: String,
    age: String,
    gender: String,
    phoneNumber: String,
    address: String,
    isEditing: Boolean,
    onNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit
){
    Card (
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults
            .cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ){
        Column (
            modifier = Modifier
                .padding(24.dp)
        ){
            Text(
                "Personal Information",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProfileField(
                label = "Full Name",
                value = fullName,
                isEditing = isEditing,
                onValueChange = onNameChange,
                icon = Icons.Default.Person
            )

            ProfileField(
                label = "Email",
                value = email,
                isEditing = false, // Email shouldn't be editable here
                onValueChange = {},
                icon = Icons.Default.Email
            )

            ProfileField(
                label = "Age",
                value = age,
                isEditing = isEditing,
                onValueChange = onAgeChange,
                icon = Icons.Default.DateRange,
                keyboardType = KeyboardType.Number
            )

            ProfileField(
                label = "Gender",
                value = gender,
                isEditing = isEditing,
                onValueChange = onGenderChange,
                icon = Icons.Default.Face
            )

            ProfileField(
                label = "Phone Number",
                value = phoneNumber,
                isEditing = isEditing,
                onValueChange = onPhoneChange,
                icon = Icons.Default.Phone,
                keyboardType = KeyboardType.Phone
            )

            ProfileField(
                label = "Address",
                value = address,
                isEditing = isEditing,
                onValueChange = onAddressChange,
                icon = Icons.Default.LocationOn,
                singleLine = false
            )
        }
    }
}