package com.example.motorsportapp.presentation.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.presentation.ui.BottomNavBar

@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    userId: Long
) {
    var metodoPago by remember { mutableStateOf("Débito") }

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Título
            Text("Tu carrito", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))

            // Carrito vacío
            Text("No hay vehículos en el carrito.", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(16.dp))

            // Método de pago
            Text("Método de pago:", style = MaterialTheme.typography.titleMedium)
            Row {
                listOf("Débito", "Transferencia", "PayPal").forEach { metodo ->
                    RadioButton(
                        selected = metodoPago == metodo,
                        onClick = { metodoPago = metodo }
                    )
                    Text(
                        text = metodo,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            // Botón de pagar
            Button(
                onClick = { /* acción de pago vacía */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pagar")
            }

            Spacer(Modifier.height(8.dp))

            // Mensaje de éxito (simulado)
            if (cartViewModel.compraExitosa) {
                Text(
                    "¡Compra exitosa!",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
