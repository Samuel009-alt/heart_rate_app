package com.example.heart_rate_app.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true
){
    Column (
        modifier = Modifier
            .padding(vertical = 8.dp)
    ){
        if (isEditing){
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                leadingIcon = { Icon(icon, contentDescription = label) },
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = singleLine,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        } else {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme
                        .colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        label,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        value.ifEmpty { "Not set" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Divider(
                color = MaterialTheme
                    .colorScheme.outline.copy(alpha = 0.3f)
            )
        }
    }
}