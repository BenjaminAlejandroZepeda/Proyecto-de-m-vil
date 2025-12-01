package com.example.motorsportapp.domain.model

data class Review(
    val id: Long,
    val comentario: String,
    val puntuacion: Int,
    val fecha: String,
    val user: UserBasic,
    val vehicleId: String
)


data class UserBasic(
    val username: String
)
