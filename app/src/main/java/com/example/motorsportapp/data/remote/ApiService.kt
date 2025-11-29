package com.example.motorsportapp.data.remote

import com.example.motorsportapp.data.remote.dto.LoginRequest
import com.example.motorsportapp.data.remote.dto.LoginResponse
import com.example.motorsportapp.data.remote.dto.RegisterRequest
import com.example.motorsportapp.data.remote.dto.RegisterResponse
import com.example.motorsportapp.domain.model.Vehicle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Auth
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    // Vehículos
    @GET("api/vehicles")
    suspend fun getVehicles(): List<Vehicle>
}
