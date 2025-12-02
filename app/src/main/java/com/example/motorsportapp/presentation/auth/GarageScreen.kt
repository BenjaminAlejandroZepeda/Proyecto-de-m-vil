package com.example.motorsportapp.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.presentation.ui.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageScreen(
    navController: NavHostController,
    vehicles: List<Vehicle>
) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Mi Garaje", style = MaterialTheme.typography.headlineMedium)

            if (vehicles.isEmpty()) {
                Text("No tienes vehículos en tu garaje.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(vehicles) { vehicle ->
                        VehicleCard(vehicle) {
                            // Navegar a reseñar este vehículo
                            navController.navigate("review/${vehicle.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleCard(vehicle: Vehicle, onReviewClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("${vehicle.manufacturer} ${vehicle.model}", style = MaterialTheme.typography.titleMedium)
            Text("Precio: $${vehicle.price}")
            Text("Asientos: ${vehicle.seats}")

            Spacer(Modifier.height(8.dp))

            Button(onClick = onReviewClick) {
                Text("Calificar 🚗⭐")
            }
        }
    }
}