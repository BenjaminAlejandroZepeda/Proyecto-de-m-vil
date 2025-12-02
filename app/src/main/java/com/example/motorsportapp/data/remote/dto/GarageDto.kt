package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GarageDto(
    val id: Long?,
    val fechaCompra: String?,
    val user: UserDto?,
    val vehicle: VehicleDto2?
)

@Serializable
data class GarageRequest(
    val vehicleId: String
)
