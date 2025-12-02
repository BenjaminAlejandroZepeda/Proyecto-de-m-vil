package com.example.motorsportapp.presentation.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.data.remote.dto.ReviewDto
import android.util.Log


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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Calificar vehículo", style = MaterialTheme.typography.headlineMedium)

        Row {
            (1..5).forEach { star ->
                IconButton(onClick = { puntuacion = star }) {
                    Icon(
                        imageVector = if (star <= puntuacion) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        OutlinedTextField(
            value = comentario,
            onValueChange = { comentario = it },
            label = { Text("Comentario") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (puntuacion > 0 && comentario.isNotBlank()) {
                    Log.d("GarageCalificarScreen", "submitReview con userId=$userId, vehicleId=$vehicleId")
                    reviewViewModel.submitReview(userId, vehicleId, comentario, puntuacion)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar reseña")
        }


        if (success) {
            Text("¡Reseña guardada exitosamente!", color = MaterialTheme.colorScheme.primary)
        }
        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        Text("Reseñas existentes", style = MaterialTheme.typography.titleMedium)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reviews) { review: ReviewDto ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("${review.user.username} ⭐${review.puntuacion}")
                        Text(review.comentario)
                        Text("Fecha: ${review.fecha}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}