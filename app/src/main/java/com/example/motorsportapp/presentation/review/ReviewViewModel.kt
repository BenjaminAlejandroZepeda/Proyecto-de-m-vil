
package com.example.motorsportapp.presentation.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motorsportapp.data.repository.ReviewRepository
import com.example.motorsportapp.domain.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val repo: ReviewRepository
) : ViewModel() {


    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun loadReviews(vehicleId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _reviews.value = repo.getReviews(vehicleId)
            } catch (e: Exception) {
                _reviews.value = emptyList()
                _error.value = e.message ?: "Error al cargar reseñas"
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun getReviews(vehicleId: String): List<Review> {
        return repo.getReviews(vehicleId)
    }
}
