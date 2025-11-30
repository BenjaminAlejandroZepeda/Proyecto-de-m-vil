package com.example.motorsportapp.presentation.vehicle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.ui.theme.SuccessColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import com.example.motorsportapp.ui.theme.TextSecondary
import androidx.compose.foundation.clickable
import com.example.motorsportapp.ui.theme.PrimaryColor
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart

@Composable
fun VehicleCard(
    vehicle: Vehicle,
    viewModel: VehicleViewModel,
    modifier: Modifier = Modifier,
    showAddToCart: Boolean = true
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            AsyncImage(
                model = vehicle.images.frontQuarter,
                contentDescription = "${vehicle.manufacturer} ${vehicle.model}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Asientos: ${vehicle.seats}", style = MaterialTheme.typography.bodySmall)
                Text("Veloc. máx: ${vehicle.topSpeed.kmh} km/h", style = MaterialTheme.typography.bodySmall)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val isFavorite = viewModel.favoriteIds.collectAsState().value.contains(vehicle.id)
                val isInCart = viewModel.cartIds.collectAsState().value.contains(vehicle.id)

                // Botón de favoritos
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.toggleFavorite(vehicle.id) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Favorito",
                        tint = if (isFavorite) PrimaryColor else TextSecondary
                    )
                }

                // Botón de carrito (solo si showAddToCart = true)
                if (showAddToCart) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.toggleCart(vehicle) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isInCart) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = if (isInCart) SuccessColor else TextSecondary
                        )
                    }
                }
            }
        }
    }
}
