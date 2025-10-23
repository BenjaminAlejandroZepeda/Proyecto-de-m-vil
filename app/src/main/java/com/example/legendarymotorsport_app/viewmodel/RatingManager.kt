package com.example.legendarymotorsport_app.viewmodel

import com.example.legendarymotorsport_app.model.Vehicle

data class Rating(
    val vehicle: Vehicle,
    val stars: Int,
    val comment: String
)

object RatingManager {
    private val ratings = mutableListOf<Rating>()

    fun addRating(vehicle: Vehicle, stars: Int, comment: String) {
        ratings.add(Rating(vehicle, stars, comment))
    }

    fun getRatings(): List<Rating> = ratings
}