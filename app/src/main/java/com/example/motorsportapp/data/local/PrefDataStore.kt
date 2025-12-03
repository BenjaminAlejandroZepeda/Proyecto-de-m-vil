package com.example.motorsportapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class PrefDataStore(private val context: Context) {

    companion object {
        val TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val USER_ID = longPreferencesKey("user_id")
    }

    suspend fun saveUserData(token: String?, username: String?, email: String?, userId: Long?) {
        context.dataStore.edit { prefs ->
            if (token != null) prefs[TOKEN] = token
            if (username != null) prefs[USERNAME] = username
            if (email != null) prefs[EMAIL] = email
            if (userId != null) prefs[USER_ID] = userId
        }
    }


    val getToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[TOKEN]
    }

    val getUsername: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USERNAME]
    }

    val getEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[EMAIL]
    }

    val getUserId: Flow<Long?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID]
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}