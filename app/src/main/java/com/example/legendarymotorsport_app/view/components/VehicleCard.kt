package com.example.legendarymotorsport_app.view.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legendarymotorsport_app.model.Vehicle
import com.example.legendarymotorsport_app.viewmodel.FavoritesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun VehicleCard(
    vehicle: Vehicle,
    onViewDetail: (Vehicle) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            var bitmap by remember { mutableStateOf<Bitmap?>(null) }

            LaunchedEffect(vehicle.images.frontQuarter) {
                bitmap = loadBitmapFromUrl(vehicle.images.frontQuarter)
            }

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = vehicle.model,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            } ?: Text(
                text = "Cargando imagen...",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .wrapContentHeight(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(vehicle.model.uppercase(), fontWeight = FontWeight.Bold)
            Text("Fabricante: ${vehicle.manufacturer.uppercase()}")
            Text("Velocidad Máx: ${vehicle.topSpeedKmh} km/h")
            Text("Asientos: ${vehicle.seats}")
            Text(
                "Precio: $${"%,d".format(vehicle.price)}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onViewDetail(vehicle) }, modifier = Modifier.fillMaxWidth()) {
                Text("Ver detalle")
            }

            Spacer(modifier = Modifier.height(4.dp))
            val isFavorite = FavoritesManager.isFavorite(vehicle)
            Button(
                onClick = { FavoritesManager.toggleFavorite(vehicle) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFavorite) Color(0xFFEF5350) else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos")
            }
        }
    }
}

suspend fun loadBitmapFromUrl(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val stream = URL(url).openStream()
            BitmapFactory.decodeStream(stream)
        } catch (e: Exception) {
            null
        }
    }
}