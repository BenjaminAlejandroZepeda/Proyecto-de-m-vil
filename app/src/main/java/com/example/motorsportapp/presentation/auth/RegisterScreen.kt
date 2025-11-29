package com.example.motorsportapp.presentation.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.motorsportapp.data.repository.UserRepository
import com.example.motorsportapp.ui.theme.*

@Composable
fun RegisterScreen(
    navController: NavController,
    repository: UserRepository
) {
    val factory = AuthViewModelFactory(repository)
    val authVm: AuthViewModel = viewModel(factory = factory)

    val uiState by authVm.registerState.collectAsState()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loading = uiState is AuthUiState.Loading
    val scale by animateFloatAsState(if (loading) 0.98f else 1f)

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundMain)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundAlt)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Registro",
                        fontSize = 28.sp,
                        color = SecondaryColor,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(Modifier.height(20.dp))

                    if (uiState is AuthUiState.Error) {
                        Text(
                            (uiState as AuthUiState.Error).error,
                            color = ErrorColor,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Nombre de usuario", color = SecondaryColor) },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = TextPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico", color = SecondaryColor) },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = TextPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña", color = SecondaryColor) },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = TextPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = { authVm.register(username, email, password) },
                        enabled = !loading,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .scale(scale)
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = BackgroundMain
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Registrando...", fontSize = 16.sp, color = TextPrimary)
                        } else {
                            Text("Registrarse", fontSize = 16.sp, color = TextPrimary)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    TextButton(onClick = { navController.navigate("login") }) {
                        Text(
                            "¿Ya tienes cuenta? Inicia sesión",
                            color = SecondaryColor,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
