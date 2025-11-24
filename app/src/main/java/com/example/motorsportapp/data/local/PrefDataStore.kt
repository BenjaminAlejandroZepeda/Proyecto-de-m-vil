package com.example.motorsportapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class PrefDataStore(private val context: Context) {

    companion object {
        val TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveUserData(token: String?, username: String?, email: String?) {
        context.dataStore.edit { prefs ->
            if (token != null) prefs[TOKEN] = token
            if (username != null) prefs[USERNAME] = username
            if (email != null) prefs[EMAIL] = email
        }
    }

    // --- LEER DATOS (FLOWS) ---
    val getToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[TOKEN]
    }

    val getUsername: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USERNAME]
    }

    val getEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[EMAIL]
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
