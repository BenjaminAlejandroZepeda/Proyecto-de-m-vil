package com.example.motorsportapp.presentation.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.data.remote.dto.ReviewDto
import com.example.motorsportapp.ui.theme.*

@Composable
fun GarageCalificarScreen(
    navController: NavHostController,
    vehicleId: String,
    userId: Long,
    reviewViewModel: ReviewViewModel
) {
    var comentario by remember { mutableStateOf("") }
    var puntuacion by remember { mutableStateOf(0) }

    val reviews by reviewViewModel.reviews.collectAsState()
    val success by reviewViewModel.success.collectAsState()
    val error by reviewViewModel.error.collectAsState()

    LaunchedEffect(vehicleId) {
        reviewViewModel.loadReviews(vehicleId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Botón volver
        item {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text("Volver", color = TextPrimary)
            }
        }

        // Header
        item {
            Text(
                "Calificar vehículo",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary
            )
        }

        // Estrellas de puntuación
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                (1..5).forEach { star ->
                    IconButton(onClick = { puntuacion = star }) {
                        Icon(
                            imageVector = if (star <= puntuacion) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = null,
                            tint = PrimaryColor
                        )
                    }
                }
            }
        }

        // Campo de comentario
        item {
            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                label = { Text("Comentario") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Botón guardar
        item {
            Button(
                onClick = {
                    if (puntuacion > 0 && comentario.isNotBlank()) {
                        reviewViewModel.submitReview(userId, vehicleId, comentario, puntuacion)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar reseña")
            }
        }

        // Mensajes de éxito/error
        if (success) {
            item {
                Text("¡Reseña guardada exitosamente!", color = SuccessColor)
            }
        }
        error?.let { err ->
            item {
                Text(err, color = ErrorColor)
            }
        }

        // Spacer antes de reviews
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Reseñas existentes",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        }

        // Lista de reviews
        items(reviews) { review: ReviewDto ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = BackgroundMain),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "${review.user.username} ⭐${review.puntuacion}",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(review.comentario, color = TextSecondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Fecha: ${review.fecha}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
        }
    }
}
