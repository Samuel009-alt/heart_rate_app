package com.example.heart_rate_app.data.repositories

import com.example.heart_rate_app.data.models.HeartRateReading
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class HeartRateRepository {

    private val database = Firebase.database.reference

    // GET HEART RATE HISTORY - FIXED VERSION
    suspend fun getHeartRateHistory(uid: String): List<HeartRateReading> {
        return try {
            val snapshot = database.child("heartRateReadings").child(uid).get().await()

            snapshot.children.mapNotNull {
                it.getValue(HeartRateReading::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // SAVE HEART RATE READING - FIXED VERSION
    suspend fun saveHeartRateReading(uid: String, reading: HeartRateReading): Boolean {
        return try {
            database.child("heartRateReadings").child(uid)
                .push()
                .setValue(reading)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}