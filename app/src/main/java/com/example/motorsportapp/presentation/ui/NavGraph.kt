package com.example.motorsportapp.presentation.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.motorsportapp.data.repository.UserRepository
import com.example.motorsportapp.presentation.auth.LoginScreen
import com.example.motorsportapp.presentation.auth.RegisterScreen
import com.example.motorsportapp.presentation.home.HomeScreen

@Composable
fun AppNavHost(context: Context, startDestination: String = "login") {
    val navController = rememberNavController()

    // Creamos el repositorio una sola vez y lo pasamos a los screens
    val repository = UserRepository(context)

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController, repository) }
        composable("register") { RegisterScreen(navController, repository) }
        composable("home") { HomeScreen() }
    }
}
