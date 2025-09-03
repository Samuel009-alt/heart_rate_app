package com.example.heart_rate_app.data.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HeartRateReading(
    val bpm: Int = 0,
    val timestamp: Long = 0L,
    val date: String = ""
){
    // Empty constructor for Firebase
    constructor() : this(0, 0L, "")
    // Helper function to create with current date
    fun withCurrentDate(): HeartRateReading {
        val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
        return this.copy(date = dateFormat.format(Date()))
    }
}