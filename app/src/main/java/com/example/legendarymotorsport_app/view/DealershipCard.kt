package com.example.legendarymotorsport_app.view

import android.location.Location
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legendarymotorsport_app.model.Dealership
import java.util.Calendar
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight



@Composable
fun DealershipCard(dealer: Dealership, userLocation: Location?) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val isOpen = currentHour in dealer.openHour until dealer.closeHour
    val statusColor = if (isOpen) Color(0xFF198754) else Color(0xFFDC3545)
    val statusText = if (isOpen) "ABIERTO" else "CERRADO"

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(dealer.imageUrl) {
        bitmap = loadBitmapFromUrl(dealer.imageUrl)
    }

    val distanceKm = userLocation?.let {
        val results = FloatArray(1)
        Location.distanceBetween(
            it.latitude, it.longitude,
            dealer.latitude, dealer.longitude,
            results
        )
        "%.2f km".format(results[0] / 1000)
    } ?: "Ubicación desconocida"

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = dealer.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            } ?: Text("Cargando imagen...", modifier = Modifier.height(180.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Text(dealer.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(dealer.address, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Contacto: ${dealer.contact}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text("Horario: $statusText", color = statusColor, fontWeight = FontWeight.Bold)
            Text("Distancia: $distanceKm", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
