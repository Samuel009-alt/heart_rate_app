package com.example.heart_rate_app.data.repositories

import com.example.heart_rate_app.data.models.UserData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().reference


    // Get user data
    suspend fun getUserData(uid: String): UserData? {
        return try {
            val snapshot: DataSnapshot = database
                .child("users").child(uid).get().await()

            // check if user exists
            if (snapshot.exists()) {
                val fullName = snapshot.child("fullName").value as? String ?: "User"
                val email = snapshot.child("email").value as? String ?: ""
                val age = (snapshot.child("age").value as? Long)?.toInt()
                val gender = snapshot.child("gender").value as? String

                UserData(uid, fullName, email, age, gender)
            } else{
                null
            }
        } catch (e: Exception){
            null
        }
    }

    // Save user data
    suspend fun saveUserData(userData: UserData): Boolean {
        return try {
            val userMap = mapOf(
                "fullName" to userData.fullName,
                "email" to userData.email,
                "age" to userData.age,
                "gender" to userData.gender
            )
            database
                .child("users")
                .child(userData.uid!!)
                .setValue(userMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Update user profile in Realtime Database
    suspend fun updateUserProfile(userData: UserData): Boolean {
        return try {
            val userId = userData.uid ?: return false

            val updateMap = mutableMapOf<String, Any>()
            userData.fullName?.let { updateMap["fullName"] = it }
            userData.email?.let { updateMap["email"] = it }
            userData.age?.let { updateMap["age"] = it }
            userData.gender?.let { updateMap["gender"] = it }

            database.child("users").child(userId).updateChildren(updateMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}