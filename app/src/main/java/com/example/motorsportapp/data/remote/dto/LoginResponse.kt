package com.example.motorsportapp.data.remote.dto

data class LoginResponse(
    val user: UserDto,
    val token: String
)
