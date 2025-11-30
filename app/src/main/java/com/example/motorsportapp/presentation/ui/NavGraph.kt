package com.example.motorsportapp.presentation.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.motorsportapp.data.remote.RetrofitInstance
import com.example.motorsportapp.data.repository.UserRepository
import com.example.motorsportapp.data.repository.VehicleRepository
import com.example.motorsportapp.presentation.auth.AccountScreen
import com.example.motorsportapp.presentation.auth.LoginScreen
import com.example.motorsportapp.presentation.auth.RegisterScreen
import com.example.motorsportapp.presentation.cart.CartScreen
import com.example.motorsportapp.presentation.home.HomeScreen
import com.example.motorsportapp.data.local.PrefDataStore
import com.example.motorsportapp.presentation.home.LocationScreen
import com.example.motorsportapp.presentation.cart.CartViewModel
import com.example.motorsportapp.presentation.vehicle.VehicleViewModel

@Composable
fun NavGraph(context: Context, startDestination: String = "login") {
    val navController = rememberNavController()

    val prefs = PrefDataStore(context)
    val apiService = RetrofitInstance.create(prefs)
    val vehicleRepository = VehicleRepository(apiService)
    val userRepository = UserRepository(context)

    val cartViewModel = CartViewModel(apiService)
    val vehicleViewModel = VehicleViewModel(vehicleRepository)

    NavHost(navController = navController, startDestination = startDestination) {

        // Auth
        composable("login") { LoginScreen(navController, userRepository) }
        composable("register") { RegisterScreen(navController, userRepository) }

        // Home principal
        composable("home") {
            HomeScreen(
                navController = navController,
                vehicleRepository = vehicleRepository
            )
        }

        // Ubicaciones
        composable("locations") { LocationScreen(navController) }

        // Carrito
        composable("cart") {
            val userId =
                userRepository.savedUserId.collectAsState(initial = "").value
                    ?.toLongOrNull() ?: 0L

            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                vehicleViewModel = vehicleViewModel,
                userId = userId
            )
        }

        // Cuenta
        composable("account") { AccountScreen() }
    }
}
