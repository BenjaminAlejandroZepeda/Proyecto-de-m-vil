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
}
