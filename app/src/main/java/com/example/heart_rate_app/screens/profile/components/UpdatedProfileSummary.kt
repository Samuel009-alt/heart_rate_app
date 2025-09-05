package com.example.heart_rate_app.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heart_rate_app.data.models.UserData

@Composable
fun UpdatedProfileSummary(currentUser: UserData) {
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
                "Your Updated Information:",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            InfoRow("Name", currentUser.fullName ?: "Not set", Icons.Default.Person)
            InfoRow("Email", currentUser.email ?: "Not set", Icons.Default.Email)
            InfoRow("Age", currentUser.age?.toString() ?: "Not set", Icons.Default.DateRange)
            InfoRow("Gender", currentUser.gender ?: "Not set", Icons.Default.Face)
            InfoRow("Phone", currentUser.phoneNumber ?: "Not set", Icons.Default.Phone)
            InfoRow("Address", currentUser.address ?: "Not set", Icons.Default.LocationOn)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, icon: ImageVector){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
}