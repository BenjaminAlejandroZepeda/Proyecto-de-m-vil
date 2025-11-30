package com.example.motorsportapp.domain.model

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

fun loadDealerships(): List<Dealership> = listOf(
    Dealership(
        name = "Nissan Kovacs Viña del Mar",
        address = "Quillota 315, 2531115 Viña del Mar, Valparaíso",
        contact = 6008996600,
        imageUrl = "https://i.imgur.com/hGAM8Z5.jpeg",
        openHour = 9,
        closeHour = 19,
        latitude = -32.984091449495665,
        longitude = -71.27339058280005
    ),
    Dealership(
        name = "Kia Rosselot - Valparaíso",
        address = "Chacabuco 2094, 2362943 Valparaíso",
        contact = 56323140398,
        imageUrl = "https://lh3.googleusercontent.com/p/AF1QipPWu-wbjlj51_d7ay0skYN9GEValRWRgnml-UdN=w432-h240-k-no",
        openHour = 9,
        closeHour = 19,
        latitude = -33.046038151075756,
        longitude = -71.61449608290279
    ),
    Dealership(
        name = "Chevrolet Kovacs Quillota",
        address = "C. Ramón Freire 1130, 2261087 Quillota, Valparaíso",
        contact = 6008996600,
        imageUrl = "https://lh3.googleusercontent.com/p/AF1QipNl5Vn-1zXEbSMjz3_oYIuO09qNO6N-L3ixJ0b4=w408-h271-k-no",
        openHour = 8,
        closeHour = 17,
        latitude = -32.88928690380751,
        longitude = -71.24672676483772
    )
)
