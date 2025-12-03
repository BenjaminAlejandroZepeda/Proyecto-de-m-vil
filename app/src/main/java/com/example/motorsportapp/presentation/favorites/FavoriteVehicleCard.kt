package com.example.motorsportapp.presentation.favorites

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.motorsportapp.data.remote.dto.FavoriteVehicle
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.ui.theme.PrimaryColor


@Composable
fun FavoriteVehicleCard(
    favorite: FavoriteVehicle,
    viewModel: FavoritesViewModel
) {
    val vehicle: Vehicle = favorite.vehicle

    var isFav by remember { mutableStateOf(viewModel.isFavorite(vehicle.id)) }
    var justAdded by remember { mutableStateOf(false) }


    val scale by animateFloatAsState(
        targetValue = if (justAdded) 1.4f else 1f,
        animationSpec = tween(durationMillis = 300),
        finishedListener = {
            justAdded = false
        }
    )


    val iconColor by animateColorAsState(
        targetValue = if (isFav) PrimaryColor else Color.Gray,
        animationSpec = tween(durationMillis = 300)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryColor.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = vehicle.images.frontQuarter,
                contentDescription = vehicle.model,
                modifier = Modifier.size(100.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(vehicle.model, style = MaterialTheme.typography.titleMedium)
                Text("Fabricante: ${vehicle.manufacturer}", style = MaterialTheme.typography.bodySmall)
                Text("Precio: $${vehicle.price}", style = MaterialTheme.typography.bodySmall)
            }


            Icon(
                imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorito",
                tint = iconColor,
                modifier = Modifier
                    .size((28.dp.value * scale).dp)
                    .clickable {
                        if (!isFav) justAdded = true
                        viewModel.toggleFavorite(vehicle.id)
                        isFav = !isFav
                    }
            )
        }
    }
}
