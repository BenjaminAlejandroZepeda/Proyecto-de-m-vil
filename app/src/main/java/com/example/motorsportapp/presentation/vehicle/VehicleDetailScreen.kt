
package com.example.motorsportapp.presentation.vehicle

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import com.example.motorsportapp.domain.model.Vehicle
import com.example.motorsportapp.domain.model.Review
import com.example.motorsportapp.presentation.cart.CartViewModel
import com.example.motorsportapp.presentation.review.ReviewViewModel
import com.example.motorsportapp.ui.theme.PrimaryColor
import com.example.motorsportapp.ui.theme.SuccessColor
import com.example.motorsportapp.ui.theme.TextSecondary


@Composable
fun VehicleDetailScreen(
    vehicleId: String,
    vehicleViewModel: VehicleViewModel,
    reviewViewModel: ReviewViewModel,
    cartViewModel: CartViewModel,
    onClose: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val vehiclesState by vehicleViewModel.vehicles.collectAsState()
    val favoriteIds by vehicleViewModel.favoriteIds.collectAsState()

    // Estado local para reseñas cargadas
    var vehicleReviews by remember { mutableStateOf<List<Review>>(emptyList()) }

    var vehicle by remember { mutableStateOf<Vehicle?>(null) }
    var loadingVehicle by remember { mutableStateOf(true) }
    var loadingReviews by remember { mutableStateOf(true) }
    var errorVehicle by remember { mutableStateOf<String?>(null) }
    var showToast by remember { mutableStateOf(false) }

    // Cargar vehículo
    LaunchedEffect(vehicleId, vehiclesState) {
        loadingVehicle = true
        vehicle = vehiclesState.find { it.id.toString().trim() == vehicleId.trim() }
        errorVehicle = if (vehicle == null) "Vehículo no encontrado" else null
        loadingVehicle = false
    }

    // Cargar reseñas
    LaunchedEffect(vehicleId) {
        loadingReviews = true
        try {

            val dtos = reviewViewModel.getReviews(vehicleId)
            vehicleReviews = dtos.map { rv ->
                com.example.motorsportapp.domain.model.Review(
                    id = rv.id,
                    comentario = rv.comentario,
                    puntuacion = rv.puntuacion,
                    fecha = rv.fecha,
                    username = rv.user.username ?: "Desconocido"
                )
            }
        } catch (e: Exception) {
            vehicleReviews = emptyList()
        } finally {
            loadingReviews = false
        }
    }



    if (loadingVehicle) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (errorVehicle != null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(errorVehicle ?: "Vehículo no encontrado")
        }
        return
    }

    vehicle?.let { v ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Barra superior
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onClose() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    "Detalles del vehículo",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Carrusel de imágenes
            val images = listOfNotNull(v.images?.front, v.images?.rear, v.images?.side, v.images?.frontQuarter)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                images.forEach { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "${v.manufacturer} ${v.model}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(260.dp)
                            .height(190.dp)
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))


            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("${v.manufacturer} ${v.model}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
                IconButton(onClick = { vehicleViewModel.toggleFavorite(v.id) }) {
                    Icon(
                        imageVector = if (favoriteIds.contains(v.id)) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (favoriteIds.contains(v.id)) PrimaryColor else TextSecondary
                    )
                }
            }

            Spacer(Modifier.height(12.dp))


            Text("Fabricante: ${v.manufacturer}")
            Text("Modelo: ${v.model}")
            Text("Asientos: ${v.seats}")
            Text("Precio: $${v.price}")
            v.topSpeed?.let { ts -> Text("Velocidad Máxima: ${ts.kmh} km/h (${ts.mph} mph)") }

            Spacer(Modifier.height(20.dp))


            Button(
                onClick = {
                    cartViewModel.addToCart(v)
                    showToast = true
                    coroutineScope.launch {
                        kotlinx.coroutines.delay(3000)
                        showToast = false
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = SuccessColor),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Añadir al carrito") }

            Spacer(Modifier.height(24.dp))


            Text("Reseñas", style = MaterialTheme.typography.titleMedium)
            val averageRating: Int = if (vehicleReviews.isNotEmpty()) {
                vehicleReviews.map { it.puntuacion }.average().toInt() // redondea hacia abajo
            } else 0
            Text("Calificación: ⭐$averageRating", fontWeight = FontWeight.Bold)

            if (loadingReviews) {
                CircularProgressIndicator(modifier = Modifier.padding(8.dp))
            } else if (vehicleReviews.isEmpty()) {
                Text("No hay reseñas disponibles", modifier = Modifier.padding(8.dp))
            } else {
                vehicleReviews.forEach { rv ->
                    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Text("⭐ ${rv.puntuacion}", fontWeight = FontWeight.Bold)
                        Text(rv.comentario)
                        Text("Usuario: ${rv.username}", style = MaterialTheme.typography.bodySmall)
                    }
                }

            }

            Spacer(Modifier.height(12.dp))

            if (showToast) {
                Text("✅ ${v.manufacturer} ${v.model} añadido al carrito", color = SuccessColor)
            }
        }
    }
}
