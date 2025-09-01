package com.example.heart_rate_app.data.repositories

import com.example.heart_rate_app.data.models.HeartRateReading
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class HeartRateRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    // Get heart rate history
    suspend fun getHeartRateHistory(uid: String): List<HeartRateReading> {
        println("DEBUG: Getting history for user: $uid")
        return try {
            val snapshot = database.child("heartRateReadings").child(uid).get().await()
            println("DEBUG: Got snapshot: ${snapshot.exists()}") // Check if data exists

            val readings = mutableListOf<HeartRateReading>()
            for (child in snapshot.children) {
                val reading = child.getValue(HeartRateReading::class.java)
                println("DEBUG: Reading: $reading") // Print each reading
                if (reading != null) {
                    readings.add(reading)
                }
            }
            println("DEBUG: Found ${readings.size} readings")
            readings
        } catch (e: Exception) {
            println("DEBUG: Error getting history: ${e.message}")
            emptyList()
        }
//        return try {
//            val snapshot = database
//                .child("heartRateReadings")
//                .child(uid)
//                .get()
//                .await() // Fixed: proper await usage
//            snapshot.children.mapNotNull { dataSnapshot ->
//                dataSnapshot.getValue(HeartRateReading::class.java)
//            }
//        } catch (e: Exception) {
//            emptyList()
//        }
    }

    // Save heart rate reading
    suspend fun saveHeartRateReading(uid: String, reading: HeartRateReading): Boolean {
        println("DEBUG: Saving reading for user: $uid, reading: $reading")
        return try {
            database.child("heartRateReadings").child(uid).push()
                .setValue(reading).await()
            println("DEBUG: Save successful")
            true
        } catch (e: Exception) {
            println("DEBUG: Save failed: ${e.message}")
            false
        }
//        return try {
//            database
//                .child("heartRateReadings")
//                .child(uid)
//                .push()
//                .setValue(reading)
//                .await()
//            true
//        } catch (e: Exception) {
//            false
//        }
    }
}