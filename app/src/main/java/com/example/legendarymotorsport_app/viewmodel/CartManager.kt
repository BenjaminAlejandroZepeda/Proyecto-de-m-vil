package com.example.legendarymotorsport_app.model

import androidx.compose.runtime.mutableStateListOf

object CartManager {
    private val _cartItems = mutableStateListOf<Vehicle>()
    val cartItems: List<Vehicle> get() = _cartItems

    fun addToCart(vehicle: Vehicle) {
        if (!_cartItems.contains(vehicle)) {
            _cartItems.add(vehicle)
        }
    }

    fun removeFromCart(vehicle: Vehicle) {
        _cartItems.remove(vehicle)
    }

    fun clearCart() {
        _cartItems.clear()
    }

    fun getTotalPrice(): Int {
        return _cartItems.sumOf { it.price }
    }
}
