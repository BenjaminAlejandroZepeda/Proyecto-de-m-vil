package com.example.legendarymotorsport_app.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.legendarymotorsport_app.viewmodel.GarageManager
import com.example.legendarymotorsport_app.view.components.VehicleCard
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Star
import com.example.legendarymotorsport_app.model.Vehicle
import com.example.legendarymotorsport_app.viewmodel.RatingManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageScreen(onBack: () -> Unit) {
    val garage = GarageManager.getGarage()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Garaje", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        if (garage.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no has recibido vehículos.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(garage) { vehicle ->
                    var showDialog by remember { mutableStateOf(false) }

                    VehicleCard(vehicle = vehicle)

                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Calificar")
                    }

                    if (showDialog) {
                        RatingDialog(
                            vehicle = vehicle,
                            onDismiss = { showDialog = false },
                            onSubmit = { stars, comment ->
                                RatingManager.addRating(vehicle, stars, comment)
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun RatingDialog(
    vehicle: Vehicle,
    onDismiss: () -> Unit,
    onSubmit: (Int, String) -> Unit
) {
    var stars by remember { mutableStateOf(3) }
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(stars, comment)
                    onDismiss()
                },
                enabled = comment.length <= 120
            ) {
                Text("Enviar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Calificar ${vehicle.model}") },
        text = {
            Column {
                Text("Selecciona estrellas:")
                Row {
                    (1..5).forEach { i ->
                        IconToggleButton(
                            checked = i <= stars,
                            onCheckedChange = { stars = i }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (i <= stars) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { if (it.length <= 120) comment = it },
                    label = { Text("Comentario (máx 120)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
