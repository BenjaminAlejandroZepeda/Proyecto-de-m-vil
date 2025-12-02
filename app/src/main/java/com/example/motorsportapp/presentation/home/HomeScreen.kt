
package com.example.motorsportapp.presentation.home

import FavoritesViewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.motorsportapp.data.repository.VehicleRepository
import com.example.motorsportapp.presentation.favorites.FavoritesViewModel
import com.example.motorsportapp.presentation.vehicle.VehicleCard
import com.example.motorsportapp.presentation.vehicle.VehicleViewModel
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.presentation.ui.SearchBar
import com.example.motorsportapp.ui.theme.BackgroundAlt
import com.example.motorsportapp.ui.theme.BackgroundMain
import com.example.motorsportapp.ui.theme.PrimaryColor
import com.example.motorsportapp.ui.theme.TextPrimary
import com.example.motorsportapp.data.remote.RetrofitInstance
import com.example.motorsportapp.data.local.PrefDataStore
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomeScreen(
    navController: NavHostController,
    vehicleRepository: VehicleRepository
) {

    val factory = VehicleViewModel.Factory(vehicleRepository)
    val viewModel: VehicleViewModel = viewModel(factory = factory)


    val context = LocalContext.current
    val pref = remember { PrefDataStore(context) }
    val api = remember { RetrofitInstance.create(pref) }
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(api)
    )

    val vehicles by viewModel.filteredVehicles.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .background(BackgroundMain),
                onSearch = { query -> viewModel.onSearchQueryChanged(query) }
            )
        },
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundMain
    ) { contentPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryColor
                )
            } else if (vehicles.isEmpty()) {
                Text(
                    text = "No se encontraron vehículos.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(vehicles) { vehicle ->
                        VehicleCard(
                            vehicle = vehicle,
                            favoritesViewModel = favoritesViewModel,
                            navController = navController,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BackgroundAlt, shape = MaterialTheme.shapes.medium)
                        )
                    }
                }
            }
        }
    }
}
