package com.example.legendarymotorsport_app.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import com.example.legendarymotorsport_app.model.Vehicle
import com.example.legendarymotorsport_app.model.loadVehiclesFromJson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(username: String, onLogout: () -> Unit, onNavigateToMap: () -> Unit) {
    val context = LocalContext.current
    val vehicles by remember { mutableStateOf(loadVehiclesFromJson(context)) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Menú",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                DrawerButton("Catálogo") { scope.launch { drawerState.close() } }
                DrawerButton("Pedidos") { scope.launch { drawerState.close() } }
                DrawerButton("Favoritos") { scope.launch { drawerState.close() } }
                DrawerButton("Soporte") { scope.launch { drawerState.close() } }
                DrawerButton("Mapa") {
                    onNavigateToMap()
                    scope.launch { drawerState.close() }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Cerrar sesión")
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Catálogo - Legendary Motorsport") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(vehicles) { vehicle ->
                    VehicleCard(vehicle) { selectedVehicle ->
                        // 🔹 Navegación futura a pantalla de detalle
                        println("Vehículo seleccionado: ${selectedVehicle.model}")
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}

@Composable
fun VehicleCard(vehicle: Vehicle, onViewDetail: (Vehicle) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

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
            Text(
                text = vehicle.model.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Fabricante: ${vehicle.manufacturer.uppercase()}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Velocidad Máx: ${vehicle.topSpeedKmh} km/h",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Asientos: ${vehicle.seats}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Precio: $${"%,d".format(vehicle.price)}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = { onViewDetail(vehicle) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ver detalle")
            }
        }
    }
}


suspend fun loadBitmapFromUrl(url: String): android.graphics.Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val stream = URL(url).openStream()
            BitmapFactory.decodeStream(stream)
        } catch (e: Exception) {
            null
        }
    }
}
