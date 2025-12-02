package com.example.motorsportapp.data.repository


import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.domain.model.Vehicle

class VehicleRepository(private val api: ApiService) {

    suspend fun getVehicles(): List<Vehicle> {
        return try {
            api.getVehicles()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addToFavorites(vehicleId: String) {
        try {
            api.addFavorite(mapOf("vehicleId" to vehicleId))
        } catch (e: Exception) {

        }
    }

    suspend fun removeFromFavorites(vehicleId: String) {
        try {
            api.removeFavorite(vehicleId)
        } catch (e: Exception) {

        }
    }

}

