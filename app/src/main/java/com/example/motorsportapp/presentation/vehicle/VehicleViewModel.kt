package com.example.motorsportapp.presentation.vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.example.motorsportapp.data.repository.VehicleRepository
import com.example.motorsportapp.domain.model.Vehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VehicleViewModel(private val repository: VehicleRepository) : ViewModel() {

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

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
}
