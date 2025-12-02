package com.example.motorsportapp.data.remote

import com.example.motorsportapp.data.remote.dto.LoginRequest
import com.example.motorsportapp.data.remote.dto.LoginResponse
import com.example.motorsportapp.data.remote.dto.RegisterRequest
import com.example.motorsportapp.data.remote.dto.RegisterResponse
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.data.remote.dto.OrderDto
import com.example.motorsportapp.data.remote.dto.ReviewDto
import com.example.motorsportapp.data.remote.dto.GarageDto
import com.example.motorsportapp.data.remote.dto.GarageRequest
import com.example.motorsportapp.domain.model.Review
import com.example.motorsportapp.data.remote.dto.ReviewRequest
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE

interface ApiService {

    // Auth
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    // Vehículos
    @GET("api/vehicles")
    suspend fun getVehicles(): List<Vehicle>

    // Favoritos
    @POST("api/favorites")
    suspend fun addFavorite(@Body body: Map<String, String>): Response<Unit>

    @DELETE("api/favorites/vehiculo/{vehicleId}")
    suspend fun removeFavorite(@retrofit2.http.Path("vehicleId") vehicleId: String): Response<Unit>

    @POST("api/orders")
    suspend fun createOrder(@Body order: OrderDto): Response<OrderDto>

    @GET("api/orders/user/{userId}")
    suspend fun getOrdersByUserId(@Path("userId") userId: Long): Response<List<OrderDto>>

    //Favoritos

    @GET("/api/reviews/vehiculo/{vehicleId}")
    suspend fun getReviewsByVehicle(@Path("vehicleId") vehicleId: String): List<ReviewDto>

    // Garaje
    @GET("api/garage")
    suspend fun getGarageForCurrentUser(): Response<List<GarageDto>>

    @POST("api/garage/add")
    suspend fun addToGarage(@Body request: GarageRequest): Response<GarageDto>

    // reviews

    @POST("api/reviews")
    suspend fun createReview(@Body review: ReviewRequest): Response<ReviewDto>



}
