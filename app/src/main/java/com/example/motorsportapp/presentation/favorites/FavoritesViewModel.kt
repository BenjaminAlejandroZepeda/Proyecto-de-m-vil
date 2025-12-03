
package com.example.motorsportapp.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.data.remote.dto.FavoriteDTO
import com.example.motorsportapp.data.remote.dto.FavoriteVehicle
import com.example.motorsportapp.domain.model.TopSpeed
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.domain.model.VehicleImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteVehicle>>(emptyList())
    val favorites: StateFlow<List<FavoriteVehicle>> = _favorites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // Evita volver a cargar si ya se cargó
    private var hasLoaded = false

    init {
        Log.d("FavoritesVM", "ViewModel creado")
    }

    fun getMyFavorites(force: Boolean = false) {
        // Si ya cargamos y no se fuerza, no vuelvas a pedir
        if (hasLoaded && !force) {
            Log.d("FavoritesVM", "getMyFavorites omitido (hasLoaded=true)")
            return
        }
        _isLoading.value = true
        Log.d("FavoritesVM", "getMyFavorites iniciado (force=$force)")
        viewModelScope.launch {
            try {
                val response: List<FavoriteDTO> = apiService.getMyFavorites()
                val mapped = response.map { dto ->
                    val v = dto.vehicle
                    FavoriteVehicle(
                        favoriteId = dto.id,
                        vehicle = Vehicle(
                            id = v.id,
                            manufacturer = v.manufacturer,
                            model = v.model,
                            seats = v.seats,
                            price = v.price, // Vehicle.price debe ser Long
                            topSpeed = TopSpeed(v.topSpeed.mph, v.topSpeed.kmh),
                            images = VehicleImages(
                                front = v.images.front,
                                rear = v.images.rear,
                                side = v.images.side,
                                frontQuarter = v.images.frontQuarter,
                                rearQuarter = v.images.rearQuarter
                            )
                        )
                    )
                }
                Log.d("FavoritesVM", "Favoritos recibidos: ${mapped.size}")
                _favorites.value = mapped
                _error.value = null
                hasLoaded = true
            } catch (e: Exception) {
                Log.e("FavoritesVM", "Error al cargar favoritos", e)
                _error.value = e.localizedMessage ?: "Error desconocido"
                // ❌ No limpiar la lista: mantenemos el último estado bueno
            } finally {
                _isLoading.value = false
                Log.d("FavoritesVM", "getMyFavorites finalizado")
            }
        }
    }

    fun toggleFavorite(vehicleId: String) {
        viewModelScope.launch {
            val current = _favorites.value
            val exists = current.any { it.vehicle.id == vehicleId }
            try {
                if (exists) {
                    // Si tu API elimina por vehicleId, esto eliminará todos los favoritos de ese vehículo
                    // Si quieres eliminar uno en particular, usa favoriteId (ajusta el endpoint).
                    apiService.removeFavorite(vehicleId)
                    _favorites.value = current.filterNot { it.vehicle.id == vehicleId }
                } else {
                    apiService.addFavorite(mapOf("vehicleId" to vehicleId))
                    // Refresca desde backend para sincronizar sin duplicados
                    getMyFavorites(force = true)
                }
                _error.value = null
            } catch (e: Exception) {
                Log.e("FavoritesVM", "Error en toggleFavorite", e)
                _error.value = e.localizedMessage ?: "Error desconocido"
                // ❌ No limpiar la lista
            }
        }
    }

    fun isFavorite(vehicleId: String): Boolean =
        _favorites.value.any { it.vehicle.id == vehicleId }
}
