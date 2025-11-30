package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemDto(
    val id: Long?,
    val cantidad: Int?,
    val precioUnitario: Int?,
    val precioTotal: Int?,
    val vehicle: VehicleDto?
)
