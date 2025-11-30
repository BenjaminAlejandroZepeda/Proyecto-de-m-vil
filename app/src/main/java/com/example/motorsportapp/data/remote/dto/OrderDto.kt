package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: Long?,
    val userId: Long?,
    val fechaPedido: String?,
    val estado: String?,
    val direccionEnvio: String?,
    val items: List<OrderItemDto>?
)
