package com.example.motorsportapp.presentation.garage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageScreen(
    navController: NavHostController,
    vehicles: List<Vehicle>
) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundAlt
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                "Mi Garaje",
                style = MaterialTheme.typography.headlineMedium.copy(color = TextPrimary),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            if (vehicles.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No tienes vehículos en tu garaje.",
                        color = TextSecondary,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(vehicles) { vehicle ->
                        VehicleCard(vehicle) {
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = BackgroundMain),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(BackgroundMain)
        ) {
            Text(
                "${vehicle.manufacturer} ${vehicle.model}",
                style = MaterialTheme.typography.titleMedium.copy(color = TextPrimary)
            )
            Spacer(Modifier.height(4.dp))
            Text("Precio: $${vehicle.price}", color = TextSecondary)
            Text("Asientos: ${vehicle.seats}", color = TextSecondary)
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onReviewClick,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calificar", color = TextPrimary)
            }
        }
    }
}
