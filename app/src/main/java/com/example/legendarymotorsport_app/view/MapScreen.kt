package com.example.legendarymotorsport_app.view

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legendarymotorsport_app.model.Dealership
import com.example.legendarymotorsport_app.model.loadDealerships
import com.google.android.gms.location.LocationServices
import com.example.legendarymotorsport_app.view.startLocationUpdates

@Composable
fun MapScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val dealerships = remember { loadDealerships() }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<Location?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                startLocationUpdates(context, fusedLocationClient) { location ->
                    Log.d("MapScreen", "Ubicación actualizada: ${location.latitude}, ${location.longitude}")
                    userLocation = location
                }
            } else {
                Log.d("MapScreen", "Permiso de ubicación denegado")
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Legendary Motors",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(dealerships) { dealer ->
                DealershipCard(dealer, userLocation)
            }
        }
    }
}
