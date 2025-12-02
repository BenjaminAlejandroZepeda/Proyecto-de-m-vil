package com.example.motorsportapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.data.remote.dto.OrderDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val apiService: ApiService
) : ViewModel() {


    private val _orders = MutableStateFlow<List<OrderDto>>(emptyList())
    val orders: StateFlow<List<OrderDto>> get() = _orders


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error


    fun loadOrders(userId: Long) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.getOrdersByUserId(userId)
                if (response.isSuccessful) {
                    _orders.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error al cargar órdenes: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}