package com.example.motorsportapp.presentation.garage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.data.remote.dto.GarageDto
import com.example.motorsportapp.data.remote.dto.VehicleDto2
import com.example.motorsportapp.domain.model.TopSpeed
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.domain.model.VehicleImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GarageViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> get() = _vehicles

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadGarage() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = apiService.getGarageForCurrentUser()
                if (response.isSuccessful) {
                    val garageItems: List<GarageDto> = response.body().orEmpty()
                    Log.d("GARAGE", "Recibidos: ${garageItems.size}")
                    _vehicles.value = garageItems.mapNotNull { dto ->
                        val v: VehicleDto2 = dto.vehicle ?: return@mapNotNull null
                        Vehicle(
                            id = v.id ?: "0",
                            manufacturer = v.manufacturer ?: "Desconocido",
                            model = v.model ?: "Desconocido",
                            price = v.price ?: 0,
                            seats = v.seats ?: 0,
                            topSpeed = v.topSpeed?.let { ts ->
                                TopSpeed(
                                    kmh = ts.kmh ?: 0,
                                    mph = ts.mph ?: 0
                                )
                            } ?: TopSpeed(0, 0),
                            images = v.images?.let { img ->
                                VehicleImages(
                                    front = img.front.orEmpty(),
                                    rear = img.rear.orEmpty(),
                                    side = img.side.orEmpty(),
                                    frontQuarter = img.frontQuarter.orEmpty(),
                                    rearQuarter = img.rearQuarter.orEmpty()
                                )
                            } ?: VehicleImages("", "", "", "", "")
                        )
                    }
                } else {
                    _error.value = "Error al cargar garaje: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}