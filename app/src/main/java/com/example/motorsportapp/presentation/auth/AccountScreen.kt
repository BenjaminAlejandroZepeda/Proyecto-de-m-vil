package com.example.motorsportapp.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.data.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    navController: NavHostController,
    userName: String?,
    userEmail: String?,
    userRepository: UserRepository
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Mi Cuenta", style = MaterialTheme.typography.headlineMedium)

            Column {
                Text("👤 Nombre: ${userName ?: ""}", style = MaterialTheme.typography.bodyLarge)
                Text("📧 Correo: ${userEmail ?: ""}", style = MaterialTheme.typography.bodyLarge)
            }

            HorizontalDivider()

            SectionItem("📄 Órdenes de Factura") { navController.navigate("orders") }
            SectionItem("🚗 Mi Garaje") { navController.navigate("garage") }
            SectionItem("❤️ Mis Favoritos") { navController.navigate("favorites") }

            Spacer(Modifier.height(24.dp))

            // 🚪 Botón de cerrar sesión
            SectionItem("🚪 Cerrar sesión") {
                coroutineScope.launch {
                    userRepository.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionItem(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(title)
    }
}
