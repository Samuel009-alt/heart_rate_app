package com.example.heart_rate_app.utils

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import okio.IOException


// DataStore Extension Property
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    companion object {
        // Preference Keys
        private val ONBOARD_COMPLETED_KEY = booleanPreferencesKey("onboard_completed")
    }

    // Save onboarding completion status
    suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        try {
            context.dataStore.edit { prefs ->
                prefs[ONBOARD_COMPLETED_KEY] = isCompleted
            }
        } catch (e: IOException){
            // Log error or handle as needed
            e.printStackTrace()
        }
    }

    // Read onboarding completion status
    val isOnboardingCompleted: Flow<Boolean> =
        context.dataStore.data
            .catch { exception ->
                // Handle exceptions and emit default value
                if (exception is IOException){
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[ONBOARD_COMPLETED_KEY] ?: false
            }

    // Clear all user data (useful for logout)
    suspend fun clearAllData() {
        try {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}