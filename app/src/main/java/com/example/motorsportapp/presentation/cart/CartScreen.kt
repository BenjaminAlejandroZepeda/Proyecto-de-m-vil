package com.example.motorsportapp.presentation.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.ui.theme.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    userId: Long
) {
    var metodoPago by remember { mutableStateOf("Débito") }
    var tipoDocumento by remember { mutableStateOf("Factura Electrónica") }

    var isProcessing by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    val total = cartViewModel.cartItems.sumOf { it.price }

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Scroll añadido
        ) {

            Text(
                "Carrito de Compras",
                style = MaterialTheme.typography.titleLarge,
                color = SecondaryColor
            )

            Spacer(Modifier.height(12.dp))


            if (cartViewModel.cartItems.isEmpty()) {
                Text("Tu carrito está vacío.")
            } else {
                cartViewModel.cartItems.forEach { vehicle ->
                    VehicleCartItem(
                        vehicle = vehicle,
                        onRemove = { cartViewModel.remove(vehicle) }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))


            Text("Método de pago", style = MaterialTheme.typography.titleMedium)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundAlt),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    listOf("Débito", "Transferencia", "PayPal").forEach { metodo ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = metodoPago == metodo,
                                onClick = { metodoPago = metodo },
                                colors = RadioButtonDefaults.colors(selectedColor = PrimaryColor)
                            )
                            Text(metodo)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Tipo de documento", style = MaterialTheme.typography.titleMedium)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundAlt),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    listOf(
                        "Factura Electrónica",
                        "Boleta Electrónica",
                        "Nota de Venta"
                    ).forEach { doc ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = tipoDocumento == doc,
                                onClick = { tipoDocumento = doc },
                                colors = RadioButtonDefaults.colors(selectedColor = PrimaryColor)
                            )
                            Text(doc)
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundAlt),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Total", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "$${total}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryColor
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    isProcessing = true
                    showSuccess = false

                    coroutineScope.launch {
                        kotlinx.coroutines.delay(1500)
                        cartViewModel.checkout(userId, "Dirección de prueba")

                        if (cartViewModel.compraExitosa) {
                            showSuccess = true
                            kotlinx.coroutines.delay(1200)
                        }

                        isProcessing = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = cartViewModel.cartItems.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (cartViewModel.cartItems.isNotEmpty()) PrimaryColor else PrimaryColor.copy(alpha = 0.5f),
                    contentColor = SecondaryColor
                )
            ) {
                Text("Pagar ahora")
            }


            Spacer(Modifier.height(20.dp))
        }

        if (isProcessing || showSuccess) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SecondaryColor.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {

                Card(
                    modifier = Modifier.padding(32.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = BackgroundMain)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isProcessing) {
                            CircularProgressIndicator(
                                color = PrimaryColor,
                                strokeWidth = 4.dp
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Procesando tu compra...",
                                style = MaterialTheme.typography.titleMedium,
                                color = SecondaryColor
                            )
                        }

                        if (showSuccess) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessColor,
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "¡Compra exitosa!",
                                style = MaterialTheme.typography.titleMedium,
                                color = SuccessColor
                            )
                        }
                    }
                }
            }
        }
    }
}
