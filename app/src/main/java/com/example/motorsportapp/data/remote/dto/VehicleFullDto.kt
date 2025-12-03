package com.example.motorsportapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto2(
    val id: String?,
    val manufacturer: String?,
    val model: String?,
    val seats: Int?,
    val price: Int?,
    val topSpeed: TopSpeedDto?,
    val images: VehicleImagesDto?
)

@Serializable
data class TopSpeedDto(
    val kmh: Int?,
    val mph: Int?
)

@Serializable
data class VehicleImagesDto(
    val front: String?,
    val rear: String?,
    val side: String?,
    val frontQuarter: String?,
    val rearQuarter: String?
)
