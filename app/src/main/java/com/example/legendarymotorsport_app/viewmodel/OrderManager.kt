package com.example.legendarymotorsport_app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import com.example.legendarymotorsport_app.model.Vehicle

object OrderManager {
    private val orders = mutableStateListOf<Vehicle>()

    fun addOrder(vehicle: Vehicle) {
        orders.add(vehicle)
    }

    fun getOrders(): List<Vehicle> = orders

    fun clearOrders() {
        orders.clear()
    }

    fun removeOrder(vehicle: Vehicle) {
        orders.remove(vehicle)
    }
}