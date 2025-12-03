package com.example.motorsportapp.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.ui.theme.BackgroundAlt
import com.example.motorsportapp.ui.theme.PrimaryColor
import com.example.motorsportapp.ui.theme.SecondaryColor
import com.example.motorsportapp.ui.theme.TextPrimary

@Composable
fun VehicleCartItem(
    vehicle: Vehicle,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundAlt)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            AsyncImage(
                model = vehicle.images.frontQuarter,
                contentDescription = vehicle.model,
                modifier = Modifier
                    .size(110.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${vehicle.manufacturer} ${vehicle.model}",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )

                Text(
                    text = "Asientos: ${vehicle.seats}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Precio: $${vehicle.price}",
                    style = MaterialTheme.typography.titleSmall,
                    color = SecondaryColor
                )

                Spacer(Modifier.height(8.dp))


                Text(
                    text = "Total: $${vehicle.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = PrimaryColor
                )
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = PrimaryColor
                )
            }
        }
    }
}
