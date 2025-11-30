package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(

    val id: Long?,
    val model: String?,
    val price: Int?

)

