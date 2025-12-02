package com.example.motorsportapp.domain.model

data class Review(
    val id: Long,
    val comentario: String,
    val puntuacion: Int,
    val fecha: String,
    val username: String
)


data class UserBasic(
    val username: String
)
