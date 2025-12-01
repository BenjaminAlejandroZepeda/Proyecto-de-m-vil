package com.example.motorsportapp.presentation.vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.repository.VehicleRepository
import com.example.motorsportapp.domain.model.Vehicle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VehicleViewModel(private val repository: VehicleRepository) : ViewModel() {

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _cart = MutableStateFlow<List<Vehicle>>(emptyList())
    val cart: StateFlow<List<Vehicle>> = _cart

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds

    private val _cartIds = MutableStateFlow<Set<String>>(emptySet())
    val cartIds: StateFlow<Set<String>> = _cartIds

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredVehicles: StateFlow<List<Vehicle>> = combine(_vehicles, _searchQuery) { vehicles, query ->
        if (query.isBlank()) vehicles
        else vehicles.filter {
            it.manufacturer.contains(query, ignoreCase = true) ||
                    it.model.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadVehicles()
    }

    private fun loadVehicles() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val list = repository.getVehicles()
                _vehicles.value = list
            } catch (e: Exception) {
                _vehicles.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    class Factory(private val repository: VehicleRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VehicleViewModel::class.java)) {
                return VehicleViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun addVehicleToFavorites(vehicleId: String) {
        viewModelScope.launch {
            repository.addToFavorites(vehicleId)
        }
    }

    fun removeVehicleFromFavorites(vehicleId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(vehicleId)
        }
    }

    fun toggleFavorite(vehicleId: String) {
        viewModelScope.launch {
            if (_favoriteIds.value.contains(vehicleId)) {
                repository.removeFromFavorites(vehicleId)
                _favoriteIds.value -= vehicleId
            } else {
                repository.addToFavorites(vehicleId)
                _favoriteIds.value += vehicleId
            }
        }
    }

    fun toggleCart(vehicle: Vehicle) {
        if (_cartIds.value.contains(vehicle.id)) {
            _cartIds.value -= vehicle.id
        } else {
            _cartIds.value += vehicle.id
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
