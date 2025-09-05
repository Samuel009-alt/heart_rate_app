package com.example.heart_rate_app.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun AvatarImage(size: Dp) {
    Box (
        modifier = Modifier
            .size(size)
            .background(MaterialTheme
                .colorScheme.primary.copy(alpha = 0.1f), CircleShape
            ),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size * 0.6f)
        )
    }
}