package com.example.motorsportapp.presentation.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.data.remote.dto.ReviewDto
import com.example.motorsportapp.data.remote.dto.ReviewRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.motorsportapp.data.remote.dto.UserIdWrapper
import com.example.motorsportapp.data.remote.dto.VehicleIdWrapper

class ReviewViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _reviews = MutableStateFlow<List<ReviewDto>>(emptyList())
    val reviews: StateFlow<List<ReviewDto>> = _reviews

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun loadReviews(vehicleId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _reviews.value = apiService.getReviewsByVehicle(vehicleId)
            } catch (e: Exception) {
                _reviews.value = emptyList()
                _error.value = e.message ?: "Error al cargar reseñas"
            } finally {
                _loading.value = false
            }
        }
    }

    fun submitReview(userId: Long, vehicleId: String, comentario: String, puntuacion: Int) {
        viewModelScope.launch {
            try {
                val request = ReviewRequest(
                    comentario = comentario,
                    puntuacion = puntuacion,
                    user = UserIdWrapper(userId),
                    vehicle = VehicleIdWrapper(vehicleId)
                )

                val response = apiService.createReview(request)

                if (response.isSuccessful) {
                    _success.value = true
                    loadReviews(vehicleId)
                } else {
                    _error.value = "Error al guardar reseña: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de red: ${e.localizedMessage}"
            }
        }
    }

    // Lo usa VehicleDetailScreen
    suspend fun getReviews(vehicleId: String): List<ReviewDto> {
        return try {
            apiService.getReviewsByVehicle(vehicleId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
