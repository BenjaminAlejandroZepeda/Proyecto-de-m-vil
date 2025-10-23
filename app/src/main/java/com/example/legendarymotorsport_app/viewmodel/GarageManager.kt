package com.example.legendarymotorsport_app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import com.example.legendarymotorsport_app.model.Vehicle

object GarageManager {
    private val garage = mutableStateListOf<Vehicle>()

    fun addToGarage(vehicle: Vehicle) {
        if (!garage.contains(vehicle)) garage.add(vehicle)
    }

    fun getGarage(): List<Vehicle> = garage

    fun clearGarage() {
        garage.clear()
    }
}