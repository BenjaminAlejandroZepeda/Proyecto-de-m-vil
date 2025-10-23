package com.example.legendarymotorsport_app.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.legendarymotorsport_app.model.CartManager
import com.example.legendarymotorsport_app.model.Vehicle
import com.example.legendarymotorsport_app.view.components.AppDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.legendarymotorsport_app.viewmodel.OrderManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val cartItems by remember { mutableStateOf(CartManager.cartItems) }
    val totalPrice = CartManager.getTotalPrice()

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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Carrito de Compras", color = androidx.compose.ui.graphics.Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = androidx.compose.ui.graphics.Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                if (cartItems.isEmpty()) {
                    Text("Tu carrito está vacío.")
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cartItems) { vehicle ->
                            CartItemRow(vehicle)
                        }
                    }

                    Divider()
                    Spacer(Modifier.height(8.dp))
                    Text("Total: $${"%,d".format(totalPrice)}", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            cartItems.forEach { OrderManager.addOrder(it) }
                            CartManager.clearCart()
                            onNavigate("orders")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Pagar")
                    }

                }
            }
        }
    }
}

@Composable
fun CartItemRow(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(vehicle.model.uppercase(), style = MaterialTheme.typography.titleMedium)
                Text("Precio: $${"%,d".format(vehicle.price)}")
            }
            IconButton(onClick = { CartManager.removeFromCart(vehicle) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
