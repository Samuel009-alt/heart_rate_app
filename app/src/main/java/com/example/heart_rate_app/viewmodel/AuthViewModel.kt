package com.example.heart_rate_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heart_rate_app.data.models.HeartRateReading
import com.example.heart_rate_app.data.models.UserData
import com.example.heart_rate_app.data.repositories.AuthRepository
import com.example.heart_rate_app.data.repositories.HeartRateRepository
import com.example.heart_rate_app.data.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository(),
    private val heartRateRepository: HeartRateRepository = HeartRateRepository()
): ViewModel() {

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

    // SIGN IN
    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val (success, errorMessage) = authRepository.signIn(email, password)

                if (success) {
                    val uid = authRepository.getCurrentUserId()
                    if (uid != null) {
                        val userData = userRepository.getUserData(uid)
                        if (userData != null) {
                            _currentUser.value = userData
                        } else {
                            val defaultUser = UserData(uid, "User", email, null, null)
                            userRepository.saveUserData(defaultUser)
                            _currentUser.value = defaultUser
                        }
                        _isLoading.value = false
                        delay(500)
                        onSuccess()
                    } else {
                        onError("Could not get user ID")
                    }
                } else {
                    onError(errorMessage ?: "Sign in failed")
                }
            } catch (e: Exception) {
                onError("Login failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // SIGN UP
    fun signUp(fullName: String, email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val (success, errorMessage) = authRepository.signUp(email, password)

                if (success) {
                    val uid = authRepository.getCurrentUserId()
                    if (uid != null) {
                        val newUser = UserData(uid, fullName, email, null, null)
                        val saved = userRepository.saveUserData(newUser)
                        if (saved) {
                            _currentUser.value = newUser
                            _isLoading.value = false
                            delay(500)
                            onSuccess()
                        } else {
                            onError("Failed to save user data")
                        }
                    } else {
                        onError("Could not get user ID")
                    }
                } else {
                    onError(errorMessage ?: "Sign up failed")
                }
            } catch (e: Exception) {
                onError("Registration failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // UPDATE PROFILE
    fun updateUserProfile(updatedUser: UserData, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = userRepository.updateUserProfile(updatedUser)
            if (success) {
                _currentUser.value = updatedUser
            }
            onResult(success)
        }
    }

    // SAVE HEART RATE
    fun saveHeartRateReading(bpm: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val uid = authRepository.getCurrentUserId() ?: run {
                    _isLoading.value = false
                    onResult(false)
                    return@launch
                }

                val reading = HeartRateReading(
                    bpm = bpm,
                    timestamp = System.currentTimeMillis()
                ).withCurrentDate() // Add this extension method call

                val success = heartRateRepository.saveHeartRateReading(uid, reading)
                if (success) {
                    // Refresh history immediately after saving
                    fetchHeartRateHistory()
                }
                onResult(success)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error saving heart rate: ${e.message}")
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // FETCH HEART RATE HISTORY
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

    // SIGN OUT
    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
        _heartRateHistory.value = emptyList()
    }

    // CHECK IF USER IS LOGGED IN
    fun isUserLoggedIn(): Boolean = authRepository.isUserLoggedIn()
}

