package com.example.legendarymotorsport_app.model

data class Dealership(
    val name: String,
    val address: String,
    val contact: Long,
    val imageUrl: String,
    val openHour: Int,
    val closeHour: Int,
    val latitude: Double,
    val longitude: Double
)
