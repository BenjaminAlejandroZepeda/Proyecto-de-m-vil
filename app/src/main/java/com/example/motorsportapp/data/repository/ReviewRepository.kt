package com.example.motorsportapp.data.repository

import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.domain.model.Review
import com.example.motorsportapp.presentation.review.toDomain

class ReviewRepository(private val api: ApiService) {

    suspend fun getReviews(vehicleId: String): List<Review> {

        return api.getReviewsByVehicle(vehicleId).map { it.toDomain() }
    }
}

