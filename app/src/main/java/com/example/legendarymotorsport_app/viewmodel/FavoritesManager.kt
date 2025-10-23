package com.example.legendarymotorsport_app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import com.example.legendarymotorsport_app.model.Vehicle

object FavoritesManager {
    private val favorites = mutableStateListOf<Vehicle>()

    fun addToFavorites(vehicle: Vehicle) {
        if (!favorites.contains(vehicle)) favorites.add(vehicle)
    }

    fun removeFromFavorites(vehicle: Vehicle) {
        favorites.remove(vehicle)
    }

    fun toggleFavorite(vehicle: Vehicle) {
        if (favorites.contains(vehicle)) removeFromFavorites(vehicle)
        else addToFavorites(vehicle)
    }

    fun isFavorite(vehicle: Vehicle): Boolean = favorites.contains(vehicle)

    fun getFavorites(): List<Vehicle> = favorites
}

