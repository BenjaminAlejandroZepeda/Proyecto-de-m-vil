package com.example.motorsportapp.data.remote.dto

import com.example.motorsportapp.domain.model.Vehicle

// DTO que representa un favorito desde la API
data class FavoriteDTO(
    val id: Long,
    val user: UserDto,
    val vehicle: Vehicle,
    val fechaGuardado: String
)


// Modelo de favoritos para la app (dominio)
data class FavoriteVehicle(
    val favoriteId: Long,
    val vehicle: Vehicle
)


