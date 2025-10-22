package com.example.legendarymotorsport_app.model

data class VehicleImages(
    val frontQuarter: String,
    val rearQuarter: String,
    val front: String,
    val rear: String,
    val side: String
)

data class Vehicle(
    val manufacturer: String,
    val model: String,
    val seats: Int,
    val price: Int,
    val topSpeedKmh: Int,
    val images: VehicleImages
)
