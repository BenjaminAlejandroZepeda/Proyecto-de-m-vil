// AuthViewModel.kt
package com.example.motorsportapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(private val repo: UserRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val loginState: StateFlow<AuthUiState> = _loginState

    private val _registerState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val registerState: StateFlow<AuthUiState> = _registerState

    private val _currentUserId = MutableStateFlow<Long?>(null)
    val currentUserId: StateFlow<Long?> = repo.savedUserId
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)


    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            val result = repo.login(email.trim(), password)
            if (result.isSuccess) {
                val response = result.getOrNull()
                _currentUserId.value = response?.user?.id
                Log.d("AuthViewModel", "Login OK, userId = ${_currentUserId.value}")
                _loginState.value = AuthUiState.Success("Login exitoso")
            } else {
                _loginState.value = AuthUiState.Error(
                    result.exceptionOrNull()?.message ?: "Error al iniciar sesión"
                )
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = AuthUiState.Loading
            val result = repo.register(username.trim(), email.trim(), password)
            if (result.isSuccess) {
                _registerState.value = AuthUiState.Success("Registro exitoso")
            } else {
                _registerState.value = AuthUiState.Error(
                    result.exceptionOrNull()?.message ?: "Error al registrarse"
                )
            }
        }
    }
}
