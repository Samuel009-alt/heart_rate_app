package com.example.heart_rate_app.data.models

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HeartRateReading(
    val bpm: Int = 0,
    val timestamp: Long = 0L,
    val date: String = "",
    val time: String = ""
){
    // Empty constructor for Firebase
    constructor() : this(0, 0L, "")

    // Helper function to create with current date
    fun withCurrentDate(): HeartRateReading {
        // Add a simple validation
        if (bpm <= 0) return this

        val dateFormat = SimpleDateFormat(
            "MMM dd, HH:mm",
            Locale.getDefault()
        )
        return this.copy(date = dateFormat.format(Date()))
    }
}

// Extension functions for HeartRateReading
fun HeartRateReading.getCategory(): HeartRateCategory {
    return HeartRateCategory.fromBpm(this.bpm)
}

fun HeartRateReading.getFormattedTime(): String {
    return if (date.isNotEmpty()) {
        date.substringAfter(", ") // Extract time part from "MMM dd, HH:mm"
    } else {
        val dateFormat = SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        )
        dateFormat.format(Date(timestamp))
    }
}

fun HeartRateReading.getFormattedDate(): String {
    return if (date.isNotEmpty()) {
        date.substringBefore(",") // Extract date part from "MMM dd, HH:mm"
    } else {
        val dateFormat = SimpleDateFormat(
            "MMM dd",
            Locale.getDefault()
        )
        dateFormat.format(Date(timestamp))
    }
}

// Heart Rate Category enum
enum class HeartRateCategory(
    val color: Color,
    val label: String,
    val range: IntRange
) {
    RESTING(Color(0xFF4CAF50), "Resting", 60..80),
    MODERATE(Color(0xFFFF9800), "Moderate", 81..120),
    VIGOROUS(Color(0xFFF44336), "Vigorous", 121..160),
    MAXIMUM(Color(0xFF9C27B0), "Maximum", 161..220);

    companion object {
        fun fromBpm(bpm: Int): HeartRateCategory {
            return values().firstOrNull { bpm in it.range } ?: RESTING
        }
    }
}

// Statistics data class
data class HeartRateStatistics(
    val averageBpm: Int = 0,
    val minBpm: Int = 0,
    val maxBpm: Int = 0,
    val totalReadings: Int = 0,
    val restingCount: Int = 0,
    val moderateCount: Int = 0,
    val vigorousCount: Int = 0,
    val maximumCount: Int = 0
)

// Helper function to calculate statistics from readings
fun calculateStatistics(readings: List<HeartRateReading>): HeartRateStatistics {
    if (readings.isEmpty()) return HeartRateStatistics()

    val bpmValues = readings.map { it.bpm }

    return HeartRateStatistics(
        averageBpm = bpmValues.average().toInt(),
        minBpm = bpmValues.minOrNull() ?: 0,
        maxBpm = bpmValues.maxOrNull() ?: 0,
        totalReadings = readings.size,
        restingCount = readings.count {
            it.getCategory() == HeartRateCategory.RESTING },
        moderateCount = readings.count {
            it.getCategory() == HeartRateCategory.MODERATE },
        vigorousCount = readings.count {
            it.getCategory() == HeartRateCategory.VIGOROUS },
        maximumCount = readings.count {
            it.getCategory() == HeartRateCategory.MAXIMUM }
    )
}
