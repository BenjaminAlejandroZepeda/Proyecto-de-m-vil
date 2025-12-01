package com.example.motorsportapp.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.motorsportapp.data.repository.VehicleRepository
import com.example.motorsportapp.presentation.vehicle.VehicleViewModel
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.presentation.ui.SearchBar
import com.example.motorsportapp.presentation.vehicle.VehicleCard

@Composable
fun HomeScreen(
    navController: NavHostController,
    vehicleRepository: VehicleRepository
) {

    val factory = VehicleViewModel.Factory(vehicleRepository)
    val viewModel: VehicleViewModel = viewModel(factory = factory)

    val vehicles by viewModel.vehicles.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier.statusBarsPadding(),
                onSearch = { query -> viewModel.onSearchQueryChanged(query) }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { contentPadding ->

        val vehicles by viewModel.filteredVehicles.collectAsState()

        LazyColumn(
            modifier = Modifier.padding(
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding()
            )
        ) {
            items(vehicles) { vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

