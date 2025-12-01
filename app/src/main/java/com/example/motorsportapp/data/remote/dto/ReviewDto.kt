
package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val id: Long,
    val comentario: String,
    val puntuacion: Int,
    val fecha: String,
    val user: UserBasicDto,
    val vehicle: VehicleDto?    // viene embebido
)

@Serializable
data class UserBasicDto(
    val username: String
)
