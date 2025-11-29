package com.example.motorsportapp.domain.model

data class Vehicle(
    val id: String,
    val manufacturer: String,
    val model: String,
    val seats: Int,
    val price: Int,
    val topSpeed: TopSpeed,
    val images: VehicleImages
)

data class TopSpeed(
    val mph: Int,
    val kmh: Int
)

data class VehicleImages(
    val frontQuarter: String,
    val rearQuarter: String,
    val front: String,
    val rear: String,
    val side: String
)
