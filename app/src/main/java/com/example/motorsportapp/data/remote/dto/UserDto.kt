package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable

data class UserDto(
    val id: Long?,
    val username: String?,
    val email: String,
    val role: String?,
    val lastLogin: String?
)

