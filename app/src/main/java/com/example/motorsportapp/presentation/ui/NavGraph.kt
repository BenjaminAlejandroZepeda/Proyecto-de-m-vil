
package com.example.motorsportapp.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.motorsportapp.data.local.PrefDataStore
import com.example.motorsportapp.data.remote.ApiService
import com.example.motorsportapp.data.remote.RetrofitInstance
import com.example.motorsportapp.data.repository.ReviewRepository
import com.example.motorsportapp.data.repository.UserRepository
import com.example.motorsportapp.data.repository.VehicleRepository
import com.example.motorsportapp.presentation.auth.AccountScreen
import com.example.motorsportapp.presentation.auth.LoginScreen
import com.example.motorsportapp.presentation.auth.RegisterScreen
import com.example.motorsportapp.presentation.cart.CartScreen
import com.example.motorsportapp.presentation.cart.CartViewModel
import com.example.motorsportapp.presentation.home.HomeScreen
import com.example.motorsportapp.presentation.home.LocationScreen
import com.example.motorsportapp.presentation.review.ReviewViewModel
import com.example.motorsportapp.presentation.vehicle.VehicleDetailScreen
import com.example.motorsportapp.presentation.vehicle.VehicleViewModel

// ❌ ELIMINAR este import que rompe todo:
// import com.google.firebase.appdistribution.gradle.ApiService

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavGraph(context: Context, startDestination: String = "login") {
    val navController = rememberNavController()

    // Inicialización de repositorios y ViewModels (una sola vez)
    val prefs = PrefDataStore(context)
    val apiService: ApiService = RetrofitInstance.create(prefs) // ✔ Usa tu ApiService top-level

    val vehicleRepository = VehicleRepository(apiService)
    val userRepository = UserRepository(context)
    val cartViewModel = CartViewModel(apiService)
    val vehicleViewModel = VehicleViewModel(vehicleRepository)

    NavHost(navController = navController, startDestination = startDestination) {

        // Rutas de autenticación
        composable("login") {
            LoginScreen(navController, userRepository)
        }

        composable("register") {
            RegisterScreen(navController, userRepository)
        }

        // Home principal
        composable("home") {
            HomeScreen(navController, vehicleRepository)
        }

        // Ubicaciones
        composable("locations") {
            LocationScreen(navController)
        }

        // Carrito
        composable("cart") {
            val userId = userRepository.savedUserId
                .collectAsState(initial = null)
                .value
                ?.toLongOrNull() ?: 0L

            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                userId = userId
            )
        }

        // Cuenta
        composable("account") {
            AccountScreen()
        }


        composable("vehicleDetail/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""


            val reviewRepository = ReviewRepository(apiService)
            val reviewViewModel = ReviewViewModel(reviewRepository)


            LaunchedEffect(vehicleId) {
                reviewViewModel.loadReviews(vehicleId)
            }

            val reviews = reviewViewModel.reviews.collectAsState(initial = emptyList()).value

            VehicleDetailScreen(
                vehicleId = vehicleId,
                vehicleViewModel = vehicleViewModel,
                reviewViewModel = reviewViewModel,
                cartViewModel = cartViewModel,
                onClose = { navController.popBackStack() }
            )

        }
    }
}
