package com.example.legendarymotorsport_app.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.legendarymotorsport_app.viewmodel.RatingManager
import com.example.legendarymotorsport_app.viewmodel.Rating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingsScreen(onBack: () -> Unit) {
    val ratings = RatingManager.getRatings()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calificaciones") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ratings) { rating: Rating ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(rating.vehicle.model.uppercase(), style = MaterialTheme.typography.titleMedium)
                        Text("⭐ ${rating.stars} estrellas")
                        Text("Comentario: ${rating.comment}")
                    }
                }
            }
        }
    }
}