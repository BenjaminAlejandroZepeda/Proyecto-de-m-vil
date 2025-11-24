package com.example.motorsportapp.data.remote.dto

data class RegisterResponse(
    val user: UserDto,
    val token: String
)
