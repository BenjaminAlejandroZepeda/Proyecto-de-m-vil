package com.example.motorsportapp.data.repository

import android.content.Context
import com.example.motorsportapp.data.local.PrefDataStore
import com.example.motorsportapp.data.remote.RetrofitInstance
import com.example.motorsportapp.data.remote.dto.*
import retrofit2.Response

class UserRepository(private val context: Context) {

    private val api = RetrofitInstance.api
    private val prefs = PrefDataStore(context)

    // LOGIN
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val resp: Response<LoginResponse> = api.login(LoginRequest(email, password))

            if (resp.isSuccessful && resp.body() != null) {
                val body = resp.body()!!

                // Guardar solo email/username/token
                prefs.saveUserData(
                    token = body.token,
                    username = body.user.username ?: "",
                    email = body.user.email
                )

                Result.success(body)
            } else {
                Result.failure(Exception(resp.errorBody()?.string() ?: "Error en login"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // REGISTER
    suspend fun register(username: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val resp: Response<RegisterResponse> =
                api.register(RegisterRequest(username, email, password))

            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()!!)
            } else {
                Result.failure(Exception(resp.errorBody()?.string() ?: "Error en register"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Flows opcionales
    val savedToken = prefs.getToken
    val savedUsername = prefs.getUsername
    val savedEmail = prefs.getEmail
}
