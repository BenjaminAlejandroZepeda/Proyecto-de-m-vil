package com.example.motorsportapp.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.data.remote.dto.OrderDto
import com.example.motorsportapp.presentation.ui.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavHostController,
    orders: List<OrderDto>
) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Órdenes de Factura", style = MaterialTheme.typography.headlineMedium)

            if (orders.isEmpty()) {
                Text("No tienes órdenes registradas.", style = MaterialTheme.typography.bodyLarge)
            } else {
                orders.forEach { order ->
                    var expanded by remember { mutableStateOf(false) }

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Factura #${order.id ?: "-"}", style = MaterialTheme.typography.titleMedium)
                                TextButton(onClick = { expanded = !expanded }) {
                                    Text(if (expanded) "Ocultar" else "Ver detalles")
                                }
                            }

                            // Datos básicos
                            Text("Fecha: ${order.fechaPedido ?: "-"}")
                            Text("Estado: ${order.estado ?: "-"}")

                            // Sección expandible
                            if (expanded) {
                                Spacer(Modifier.height(8.dp))
                                Text("Dirección de envío: ${order.direccionEnvio ?: "-"}")

                                Spacer(Modifier.height(8.dp))
                                Text("Items:", style = MaterialTheme.typography.titleSmall)

                                order.items?.forEach { item ->
                                    Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
                                        Text("Vehículo: ${item.vehicle?.model ?: "-"}")
                                        Text("Cantidad: ${item.cantidad ?: 0}")
                                        Text("Precio unitario: $${item.precioUnitario ?: 0}")
                                        Text("Total: $${item.precioTotal ?: 0}")
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