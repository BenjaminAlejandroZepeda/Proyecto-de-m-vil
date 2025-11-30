package com.example.motorsportapp.presentation.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.data.remote.dto.OrderDto
import com.example.motorsportapp.data.remote.dto.OrderItemDto
import com.example.motorsportapp.data.remote.dto.VehicleDto
import com.example.motorsportapp.domain.model.Vehicle
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CartViewModel(private val apiService: ApiService) : ViewModel() {

    private val _cartItems = mutableStateListOf<Vehicle>()
    val cartItems: List<Vehicle> get() = _cartItems

    var compraExitosa by mutableStateOf(false)
        private set

    fun addToCart(vehicle: Vehicle) {
        _cartItems.add(vehicle)
    }

    fun clearCart() {
        _cartItems.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkout(userId: Long, direccion: String) {
        viewModelScope.launch {
            val order = OrderDto(
                id = null,
                userId = userId,
                fechaPedido = LocalDateTime.now().toString(),
                estado = "pendiente",
                direccionEnvio = direccion,
                items = _cartItems.map { vehicle ->
                    OrderItemDto(
                        id = null,
                        cantidad = 1,
                        precioUnitario = vehicle.price,
                        precioTotal = vehicle.price,
                        vehicle = VehicleDto(
                            id = vehicle.id.toLongOrNull(), // id es String en dominio
                            model = vehicle.model,
                            price = vehicle.price
                        )
                    )
                }
            )

            val response = apiService.createOrder(order)
            if (response.isSuccessful) {
                compraExitosa = true
                clearCart()
            }
        }
    }
}
