package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    val id: String?,
    val model: String?,
    val price: Int?
)

