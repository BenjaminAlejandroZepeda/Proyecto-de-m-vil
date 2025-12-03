package com.example.motorsportapp.presentation.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.motorsportapp.data.remote.dto.OrderDto
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavHostController,
    orders: List<OrderDto>
) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundAlt
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Órdenes de Factura",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary
            )

            if (orders.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes órdenes registradas.", color = TextSecondary, fontSize = 16.sp)
                }
            } else {
                orders.forEach { order ->
                    var expanded by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = BackgroundMain),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Factura #${order.id ?: "-"}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary
                                )
                                TextButton(
                                    onClick = { expanded = !expanded }
                                ) {
                                    Text(
                                        if (expanded) "Ocultar" else "Ver detalles",
                                        color = PrimaryColor
                                    )
                                }
                            }

                            Spacer(Modifier.height(4.dp))
                            Text("Fecha: ${order.fechaPedido ?: "-"}", color = TextSecondary)
                            Text("Estado: ${order.estado ?: "-"}", color = TextSecondary)

                            if (expanded) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Dirección de envío: ${order.direccionEnvio ?: "-"}",
                                    color = TextSecondary
                                )

                                Spacer(Modifier.height(8.dp))
                                Text("Items:", style = MaterialTheme.typography.titleSmall, color = TextPrimary)

                                order.items?.forEach { item ->
                                    Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
                                        Text("Vehículo: ${item.vehicle?.model ?: "-"}", color = TextSecondary)
                                        Text("Cantidad: ${item.cantidad ?: 0}", color = TextSecondary)
                                        Text("Precio unitario: $${item.precioUnitario ?: 0}", color = TextSecondary)
                                        Text("Total: $${item.precioTotal ?: 0}", color = TextSecondary)
                                        Spacer(Modifier.height(6.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
