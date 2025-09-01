package com.example.heart_rate_app.data.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HeartRateReading(
    val bpm: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val date: String = SimpleDateFormat(
        "MMM dd, HH:mm", Locale.getDefault()
    ).format(Date())
)