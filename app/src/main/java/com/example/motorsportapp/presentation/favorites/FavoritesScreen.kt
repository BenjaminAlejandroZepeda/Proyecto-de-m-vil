
package com.example.motorsportapp.presentation.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.ui.theme.BackgroundAlt
import com.example.motorsportapp.ui.theme.PrimaryColor
import com.example.motorsportapp.ui.theme.SecondaryColor

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoritesViewModel
) {
    // Usar collectAsStateWithLifecycle evita desincronizaciones al cambiar el ciclo de vida
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    // Cargar una vez al entrar
    LaunchedEffect(Unit) {
        viewModel.getMyFavorites(force = false)
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundAlt
    ) { contentPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(Modifier.fillMaxSize()) {
                Text(
                    "Mis Favoritos",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                when {
                    favorites.isEmpty() && !isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                if (error != null) {
                                    "No se pudieron cargar los favoritos.\n$error"
                                } else {
                                    "No tienes favoritos guardados."
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = SecondaryColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(favorites, key = { it.favoriteId }) { fav ->
                                FavoriteVehicleCard(
                                    favorite = fav,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }

            if (isLoading) {
                // Loader como overlay, no borra ni reemplaza la lista
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryColor)
                }
            }
        }
    }
}
