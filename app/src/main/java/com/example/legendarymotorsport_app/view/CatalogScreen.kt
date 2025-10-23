package com.example.legendarymotorsport_app.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legendarymotorsport_app.model.Vehicle
import com.example.legendarymotorsport_app.model.loadVehiclesFromJson
import com.example.legendarymotorsport_app.view.components.AppDrawer
import com.example.legendarymotorsport_app.view.components.AppTheme
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import java.net.URL
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import com.example.legendarymotorsport_app.model.CartManager
import com.example.legendarymotorsport_app.viewmodel.FavoritesManager
import com.example.legendarymotorsport_app.view.components.VehicleCard



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    username: String,
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val allVehicles by remember { mutableStateOf(loadVehiclesFromJson(context)) }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    var searchQuery by remember { mutableStateOf("") }
    var filteredVehicles by remember { mutableStateOf(allVehicles) }
    var selectedVehicle by remember { mutableStateOf<Vehicle?>(null) }

    LaunchedEffect(searchQuery) {
        filteredVehicles = if (searchQuery.isBlank()) {
            allVehicles
        } else {
            allVehicles.filter {
                it.model.contains(searchQuery, ignoreCase = true) ||
                        it.manufacturer.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    AppTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    scope = scope,
                    drawerState = drawerState,
                    onLogout = onLogout,
                    onNavigate = onNavigate
                )
            }
        ) {
            if (selectedVehicle != null) {
                VehicleDetailScreen(vehicle = selectedVehicle!!, onBack = { selectedVehicle = null })
            } else {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Catálogo - Legendary Motorsport", color = Color.White, fontSize = 20.sp) },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(innerPadding)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Buscar por modelo o fabricante") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(filteredVehicles) { vehicle ->
                                VehicleCard(vehicle = vehicle, onViewDetail = { selectedVehicle = it })
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun VehicleCard(vehicle: Vehicle, onViewDetail: (Vehicle) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

            // 🔹 Cargar imagen en background
            LaunchedEffect(vehicle.images.frontQuarter) {
                bitmap = loadBitmapFromUrl(vehicle.images.frontQuarter)
            }

            // 🔹 Mostrar la imagen si está lista, sino placeholder
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
            Button(
                onClick = { FavoritesManager.addToFavorites(vehicle) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350))
            ) {
                Text("Agregar a favoritos")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailScreen(vehicle: Vehicle, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(vehicle.model, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
            LaunchedEffect(vehicle.images.frontQuarter) {
                bitmap = loadBitmapFromUrl(vehicle.images.frontQuarter)
            }
            bitmap?.let {
                Image(
                    it.asImageBitmap(),
                    contentDescription = vehicle.model,
                    modifier = Modifier.fillMaxWidth().height(220.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(vehicle.model.uppercase(), fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text("Fabricante: ${vehicle.manufacturer.uppercase()}")
            Text("Velocidad Máx: ${vehicle.topSpeedKmh} km/h")
            Text("Asientos: ${vehicle.seats}")
            Text(
                "Precio: $${"%,d".format(vehicle.price)}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Botón añadir al carrito
            Button(
                onClick = {
                    CartManager.addToCart(vehicle)
                    onBack() // opcional: vuelve al catálogo después de añadir
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir al carrito")
            }
        }
    }
}


suspend fun loadBitmapFromUrl(url: String): android.graphics.Bitmap? {
    return withContext(kotlinx.coroutines.Dispatchers.IO) {
        try {
            val stream = URL(url).openStream()
            BitmapFactory.decodeStream(stream)
        } catch (e: Exception) {
            null
        }
    }
}
