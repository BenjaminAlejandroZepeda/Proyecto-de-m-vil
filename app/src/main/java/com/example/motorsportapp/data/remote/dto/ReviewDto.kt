
package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val id: Long,
    val comentario: String,
    val puntuacion: Int,
    val fecha: String,
    val user: UserBasicDto,
    val vehicle: VehicleDto?
)

@Serializable
data class UserBasicDto(
    val username: String
)

data class ReviewRequest(
    val comentario: String,
    val puntuacion: Int,
    val user: UserIdWrapper,
    val vehicle: VehicleIdWrapper
)

data class UserIdWrapper(val id: Long)
data class VehicleIdWrapper(val id: String)
