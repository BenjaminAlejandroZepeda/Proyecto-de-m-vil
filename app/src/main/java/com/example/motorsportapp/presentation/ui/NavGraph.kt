
package com.example.motorsportapp.presentation.ui

import FavoritesViewModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.motorsportapp.presentation.vehicle.VehicleDetailScreen
import com.example.motorsportapp.presentation.vehicle.VehicleViewModel
import com.example.motorsportapp.presentation.order.OrdersScreen
import com.example.motorsportapp.presentation.order.OrdersViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.motorsportapp.presentation.garage.GarageScreen
import com.example.motorsportapp.presentation.garage.GarageViewModel
import com.example.motorsportapp.presentation.review.GarageCalificarScreen
import com.example.motorsportapp.presentation.auth.AuthViewModel
import com.example.motorsportapp.presentation.favorites.FavoritesScreen
import com.example.motorsportapp.presentation.favorites.FavoritesViewModel
import com.example.motorsportapp.presentation.review.ReviewViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavGraph(context: Context, startDestination: String = "login") {
    val navController = rememberNavController()

    // Inicialización de repositorios y ViewModels
    val prefs = PrefDataStore(context)
    val apiService: ApiService = RetrofitInstance.create(prefs)

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
            val userId = userRepository.savedUserId.collectAsState(initial = 0L).value ?: 0L

            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                userId = userId
            )
        }

        // Cuenta
        composable("account") {
            val userName = userRepository.savedUsername.collectAsState(initial = "").value
            val userEmail = userRepository.savedEmail.collectAsState(initial = "").value

            AccountScreen(
                navController = navController,
                userName = userName,
                userEmail = userEmail,
                userRepository = userRepository
            )
        }

        // Órdenes
        composable("orders") {
            val ordersViewModel = remember { OrdersViewModel(apiService) } // 👈 evita recrearlo

            val userId = userRepository.savedUserId.collectAsState(initial = 0L).value ?: 0L

            LaunchedEffect(userId) {
                ordersViewModel.loadOrders(userId)
            }

            val orders = ordersViewModel.orders.collectAsState().value
            val loading = ordersViewModel.loading.collectAsState().value
            val error = ordersViewModel.error.collectAsState().value

            OrdersScreen(
                navController = navController,
                orders = orders
            )

            if (loading) {
                CircularProgressIndicator()
            }
            error?.let {
                Text("⚠️ $it", color = MaterialTheme.colorScheme.error)
            }
        }

        // Garage
        composable("garage") {
            val garageViewModel = remember { GarageViewModel(apiService) }

            val userId = userRepository.savedUserId.collectAsState(initial = 0L).value ?: 0L

            LaunchedEffect(Unit) {
                garageViewModel.loadGarage()
            }

            val vehicles = garageViewModel.vehicles.collectAsState().value
            val loading = garageViewModel.loading.collectAsState().value
            val error = garageViewModel.error.collectAsState().value

            GarageScreen(navController, vehicles)

            if (loading) {
                CircularProgressIndicator()
            }
            error?.let {
                Text("⚠️ $it", color = MaterialTheme.colorScheme.error)
            }
        }

        // Favorites
        composable("favorites") {
            val context = LocalContext.current


            val pref = remember { PrefDataStore(context) }
            val api = remember { RetrofitInstance.create(pref) }

            val viewModel: FavoritesViewModel = viewModel(
                factory = FavoritesViewModelFactory(api)
            )
            FavoritesScreen(navController = navController, viewModel = viewModel)
        }


        // Calificar
        composable("review/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
            val authViewModel = remember { AuthViewModel(userRepository) }
            val userId = authViewModel.currentUserId.collectAsState().value ?: 0L

            val reviewRepository = ReviewRepository(apiService)
            val reviewViewModel = remember { ReviewViewModel(apiService) }


            GarageCalificarScreen(
                navController = navController,
                vehicleId = vehicleId,
                userId = userId,
                reviewViewModel = reviewViewModel
            )
        }

        composable("vehicleDetail/{vehicleId}") { backStackEntry ->

            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""

            val context = LocalContext.current
            val pref = remember { PrefDataStore(context) }
            val api = remember { RetrofitInstance.create(pref) }


            val favoritesViewModel: FavoritesViewModel = viewModel(
                factory = FavoritesViewModelFactory(api)
            )

            val vehicleRepository = remember { VehicleRepository(api) }
            val vehicleViewModel: VehicleViewModel = viewModel(
                factory = VehicleViewModel.Factory(vehicleRepository)
            )

            val reviewViewModel = remember { ReviewViewModel(api) }


            LaunchedEffect(vehicleId) {
                reviewViewModel.loadReviews(vehicleId)
            }

            val reviews = reviewViewModel.reviews.collectAsState(initial = emptyList()).value

            VehicleDetailScreen(
                vehicleId = vehicleId,
                vehicleViewModel = vehicleViewModel,
                favoritesViewModel = favoritesViewModel,
                reviewViewModel = reviewViewModel,


                cartViewModel = cartViewModel,

                onClose = { navController.popBackStack() }
            )
        }



    }
}

