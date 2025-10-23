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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.legendarymotorsport_app.viewmodel.OrderManager
import com.example.legendarymotorsport_app.view.components.VehicleCard
import com.example.legendarymotorsport_app.viewmodel.GarageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(onBack: () -> Unit) {
    val orders = OrderManager.getOrders()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedidos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes pedidos aún.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { vehicle ->
                    VehicleCard(vehicle = vehicle)
                    Button(
                        onClick = {
                            OrderManager.removeOrder(vehicle)
                            GarageManager.addToGarage(vehicle)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Confirmar recepción")
                    }
                }
            }
        }
    }
}
