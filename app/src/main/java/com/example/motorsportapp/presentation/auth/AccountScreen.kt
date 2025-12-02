package com.example.motorsportapp.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motorsportapp.data.repository.UserRepository
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.ui.theme.BackgroundAlt
import com.example.motorsportapp.ui.theme.BackgroundMain
import com.example.motorsportapp.ui.theme.ErrorColor
import com.example.motorsportapp.ui.theme.SecondaryColor
import com.example.motorsportapp.ui.theme.TextPrimary
import com.example.motorsportapp.ui.theme.TextSecondary
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
        containerColor = BackgroundMain,
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {


            Text(
                "Mi Cuenta",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = SecondaryColor
                )
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(BackgroundAlt)
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Usuario",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = userName ?: "",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Correo",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = userEmail ?: "",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }


            Text(
                "Pedidos",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = SecondaryColor
                )
            )


            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                AccountOptionItem(
                    title = "Lista de deseos",
                    icon = Icons.Filled.Favorite,
                    onClick = { navController.navigate("favorites") }
                )

                AccountOptionItem(
                    title = "Reseñas",
                    Icons.Outlined.Star,
                    onClick = { navController.navigate("garage") }
                )

                AccountOptionItem(
                    title = "Compras",
                    icon = Icons.Filled.ShoppingCart,
                    onClick = { navController.navigate("orders") }
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {
                    coroutineScope.launch {
                        userRepository.logout()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ErrorColor,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(39.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Cerrar sesión", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun AccountOptionItem(title: String, icon: ImageVector, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundAlt)
            .clickable { onClick() }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = SecondaryColor,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 12.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = TextPrimary)
        )
    }
}
