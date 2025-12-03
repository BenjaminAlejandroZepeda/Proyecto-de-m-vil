package com.example.motorsportapp.data.repository

import android.content.Context
import com.example.motorsportapp.data.local.PrefDataStore
import com.example.motorsportapp.data.remote.RetrofitInstance
import com.example.motorsportapp.data.remote.dto.*
import retrofit2.Response

class UserRepository(private val context: Context) {

    private val prefs = PrefDataStore(context)
    private val api = RetrofitInstance.create(prefs)

    // LOGIN
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val resp: Response<LoginResponse> = api.login(LoginRequest(email, password))

            if (resp.isSuccessful && resp.body() != null) {
                val body = resp.body()!!


                prefs.saveUserData(
                    token = body.token,
                    username = body.user.username ?: "",
                    email = body.user.email,
                    userId = body.user.id
                )

                Result.success(body)
            } else {

                val code = resp.code()
                val errorMsg = when (code) {
                    401 -> "Credenciales incorrectas"
                    403 -> "Acceso denegado"
                    400 -> "Datos inválidos"
                    else -> "Error $code: ${resp.message()}"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }


    suspend fun register(username: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val resp: Response<RegisterResponse> =
                api.register(RegisterRequest(username, email, password, "user"))

            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()!!)
            } else {

                val code = resp.code()
                val errorMsg = when (code) {
                    401 -> "No autorizado"
                    403 -> "Usuario ya registrado"
                    400 -> "Datos inválidos"
                    else -> "Error $code: ${resp.message()}"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage}"))
        }
    }


    suspend fun logout() {
        prefs.clear()
    }


    val savedToken = prefs.getToken
    val savedUsername = prefs.getUsername
    val savedEmail = prefs.getEmail
    val savedUserId = prefs.getUserId
}
