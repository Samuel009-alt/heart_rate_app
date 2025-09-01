package com.example.heart_rate_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heart_rate_app.data.models.HeartRateReading
import com.example.heart_rate_app.data.models.UserData
import com.example.heart_rate_app.data.repositories.AuthRepository
import com.example.heart_rate_app.data.repositories.HeartRateRepository
import com.example.heart_rate_app.data.repositories.UserRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val heartRateRepository: HeartRateRepository = HeartRateRepository()
): ViewModel() {

    // Firebase references
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    // User state
    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser: StateFlow<UserData?> = _currentUser.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Heart rate state
    private val _heartRateHistory = MutableStateFlow<List<HeartRateReading>>(emptyList())
    val heartRateHistory: StateFlow<List<HeartRateReading>> = _heartRateHistory.asStateFlow()

    init {
        checkCurrentUser()
    }

    // Check if user is logged in
    private fun checkCurrentUser() {
        if (authRepository.isUserLoggedIn()) {
            authRepository.getCurrentUserId()?.let { uid ->
                loadUserData(uid)
            }
        }
    }

    // Load user data
    private fun loadUserData(uid: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val userData = userRepository.getUserData(uid)
            _currentUser.value = userData
            _isLoading.value = false
        }
    }

    // Sign In
    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            val (success, errorMessage) = authRepository
                .signIn(email, password)
            if (success){
                authRepository.getCurrentUserId()?.let { uid ->
                    val userData = userRepository.getUserData(uid)
                    _currentUser.value = userData
                    _isLoading.value = false
                    onSuccess()
                } ?: run {
                    _isLoading.value = false
                    onError("Could not get user ID")
                }
            } else {
                _isLoading.value = false
                onError(errorMessage ?: "Sign in failed")
            }
        }
    }

    // Sign up
    fun signUp(fullName: String, email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            val (success, errorMessage) = authRepository
                .signUp(email, password)
            if (success){
                authRepository.getCurrentUserId()?.let { uid ->
                    val newUser = UserData(uid, fullName, email, null, null)
                    val saved = userRepository.saveUserData(newUser)

                    if (saved){
                        _currentUser.value = newUser
                        _isLoading.value = false
                        onSuccess()
                    } else {
                        _isLoading.value = false
                        onError(" Failed to save user data")
                    }
                } ?: run {
                    _isLoading.value = false
                    onError("Could not get user ID")
                }
            } else {
                _isLoading.value = false
                onError(errorMessage ?: "Sign up failed")
            }
        }
    }

    // Update user profile
    fun updateUserProfile(updatedUser: UserData, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = userRepository.updateUserProfile(updatedUser)
            if (success) {
                _currentUser.value = updatedUser
            }
            onResult(success)
        }
    }

    // Load heart rate history
    fun fetchHeartRateHistory() {
        viewModelScope.launch {
            authRepository.getCurrentUserId()?.let { uid ->
                _isLoading.value = true
                val history = heartRateRepository.getHeartRateHistory(uid)
                _heartRateHistory.value = history
                _isLoading.value = false
            }
        }
    }

    // Fixed save heart rate reading function
    fun saveHeartRateReading(bpm: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val uid = auth.currentUser?.uid ?: run {
                onResult(false)
                return@launch
            }

            val reading = HeartRateReading(bpm = bpm)

            try {
                database.child("heartRateReadings").child(uid)
                    .push()
                    .setValue(reading)
                    .await()

                // Refresh the history after saving
                fetchHeartRateHistory()
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    // Sign out
    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
        _heartRateHistory.value = emptyList()
    }

    // Check if user is logged in
    fun isUserLoggedIn(): Boolean = authRepository.isUserLoggedIn()
}