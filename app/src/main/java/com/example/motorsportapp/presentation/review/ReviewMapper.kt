package com.example.motorsportapp.presentation.review

import com.example.motorsportapp.data.remote.dto.ReviewDto
import com.example.motorsportapp.domain.model.Review
import com.example.motorsportapp.domain.model.UserBasic

fun ReviewDto.toDomain(): Review {
    return Review(
        id = this.id,
        comentario = this.comentario,
        puntuacion = this.puntuacion,
        fecha = this.fecha,
        user = UserBasic(username = this.user.username),
        vehicleId = this.vehicle?.id ?: ""
    )
}
