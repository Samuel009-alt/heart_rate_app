package com.example.heart_rate_app.data.repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

// AuthRepository.kt - Handles only authentication
class AuthRepository {

    // Firebase references
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Sign in
    suspend fun signIn(email: String, password: String): Pair<Boolean, String?> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Pair(true, null) // success, no error
        } catch (e: Exception) {
            Pair(false, e.message ?: "Sign in failed") // failure, with error message
        }
    }

    // Sign up
    suspend fun signUp(email: String, password: String): Pair<Boolean, String?> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Pair(true, null) // success, no error
        } catch (e: Exception){
            Pair(false, e.message ?: "Sign up failed") // failure, with error message
        }
    }

    // Check if user is logged in
    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    // Get current user ID
    fun getCurrentUserId(): String? = auth.currentUser?.uid


    // Sign out
    fun signOut() {
        auth.signOut()
    }
}

