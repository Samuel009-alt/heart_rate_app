package com.example.heart_rate_app.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heart_rate_app.data.models.HeartRateStatistics

@Composable
fun QuickStatsRow(
    statistics: HeartRateStatistics
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(listOf(
            Triple("Resting", statistics.restingCount, Color(0xFF27AE60)),
            Triple("Moderate", statistics.moderateCount, Color(0xFFFF9800)),
            Triple("Vigorous", statistics.vigorousCount, Color(0xFFE74C3C)),
            Triple("Maximum", statistics.maximumCount, Color(0xFF9C27B0))
        )) { (category, count, color) ->
            QuickStatCard(category, count, color)
        }
    }
}

@Composable
fun QuickStatCard(
    category: String,
    count: Int,
    color: Color
) {
    Card(
        modifier = Modifier
            .width(110.dp)
            .height(90.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = category,
                fontSize = 11.sp,
                color = color.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
