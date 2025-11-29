package com.example.motorsportapp.presentation.ui

import android.content.Context
import androidx.compose.runtime.Composable
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
import com.example.motorsportapp.presentation.home.SearchScreen

@Composable
fun AppNavHost(context: Context, startDestination: String = "login") {
    val navController = rememberNavController()

    val userRepository = UserRepository(context)
    val vehicleRepository = VehicleRepository(RetrofitInstance.api)


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

        // Rutas del BottomNav
        composable("search") { SearchScreen() }
        composable("cart") { CartScreen() }
        composable("account") { AccountScreen() }
    }
}
