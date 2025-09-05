package com.example.heart_rate_app.screens.onboarding.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.heart_rate_app.ui.theme.LightBlue
import com.example.heart_rate_app.ui.theme.LightGray
import com.example.heart_rate_app.ui.theme.OffWhite
import com.example.heart_rate_app.ui.theme.SoftBlack

@Composable
fun FeatureItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = LightGray, // Your accent color
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = SoftBlack
        )
    }
}