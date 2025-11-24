package com.example.motorsportapp.data.remote.dto

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String = "user"
)
