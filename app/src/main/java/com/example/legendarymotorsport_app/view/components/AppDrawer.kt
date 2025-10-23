package com.example.legendarymotorsport_app.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    scope: CoroutineScope,
    drawerState: DrawerState,
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Menú",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        DrawerButton("Mi Garaje") {
            scope.launch { drawerState.close() }
            onNavigate("garage")
        }


        DrawerButton("Favoritos") {
            scope.launch { drawerState.close() }
            onNavigate("favorites")
        }

        DrawerButton("Calificaciones") {
            scope.launch { drawerState.close() }
            onNavigate("ratings")
        }

        DrawerButton("Catálogo") {
            scope.launch { drawerState.close() }
            onNavigate("catalog")
        }

        DrawerButton("Carrito") {
            scope.launch { drawerState.close() }
            onNavigate("cart")
        }

        DrawerButton("Pedidos") {
            scope.launch { drawerState.close() }
            onNavigate("orders")
        }

        DrawerButton("Mapa") {
            scope.launch { drawerState.close() }
            onNavigate("map")
        }

        DrawerButton("Soporte") {
            scope.launch { drawerState.close() }
            onNavigate("support")
        }

        Spacer(modifier = Modifier.weight(1f))

        // 🔹 Logout
        DrawerButton("Cerrar sesión") {
            scope.launch { drawerState.close() }
            onLogout()
        }

    }
}

@Composable
fun DrawerButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(text)
    }
}
