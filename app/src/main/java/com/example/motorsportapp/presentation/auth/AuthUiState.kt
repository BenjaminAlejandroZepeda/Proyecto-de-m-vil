package com.example.motorsportapp.presentation.auth

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val message: String = "") : AuthUiState()
    data class Error(val error: String) : AuthUiState()
}
