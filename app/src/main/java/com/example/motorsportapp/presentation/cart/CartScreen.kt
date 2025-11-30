package com.example.motorsportapp.presentation.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.presentation.vehicle.VehicleCard
import com.example.motorsportapp.presentation.vehicle.VehicleViewModel
import com.example.motorsportapp.presentation.ui.BottomNavBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    vehicleViewModel: VehicleViewModel,
    userId: Long
) {
    var metodoPago by remember { mutableStateOf("Débito") }
    val cartItems = cartViewModel.cartItems   // ✅ usar lista directamente

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Tu carrito", style = MaterialTheme.typography.titleLarge)

            if (cartItems.isEmpty()) {
                Text("No hay vehículos en el carrito.")
            } else {
                cartItems.forEach { vehicle ->
                    VehicleCard(
                        vehicle = vehicle,
                        viewModel = vehicleViewModel,
                        showAddToCart = false
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Método de pago:")
            Row {
                listOf("Débito", "Transferencia", "PayPal").forEach {
                    RadioButton(
                        selected = metodoPago == it,
                        onClick = { metodoPago = it }
                    )
                    Text(it)
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                cartViewModel.checkout(
                    userId = userId,
                    direccion = "Av. Las Condes 12345, Santiago"
                )
            }) {
                Text("Pagar")
            }

            Spacer(Modifier.height(8.dp))

            if (cartViewModel.compraExitosa) {
                Text("¡Compra exitosa!", color = Color.Green)
            }
        }
    }
}
