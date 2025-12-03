package com.example.motorsportapp.presentation.vehicle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.motorsportapp.domain.model.Vehicle


@Composable
fun VehicleCard(
    vehicle: Vehicle,
    navController: NavController,
    modifier: Modifier = Modifier
) {


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                navController.navigate("vehicleDetail/${vehicle.id}")
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            Column(modifier = Modifier.padding(12.dp)) {

                AsyncImage(
                    model = vehicle.images.frontQuarter,
                    contentDescription = "${vehicle.manufacturer} ${vehicle.model}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${vehicle.manufacturer} ${vehicle.model}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Precio: ${vehicle.price}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Asientos: ${vehicle.seats} | Vel. máx: ${vehicle.topSpeed.kmh} km/h",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
