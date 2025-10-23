package com.example.legendarymotorsport_app.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import com.example.legendarymotorsport_app.model.User
import com.example.legendarymotorsport_app.view.CatalogScreen
import com.example.legendarymotorsport_app.view.LoginScreen
import com.example.legendarymotorsport_app.view.RegisterScreen
import com.example.legendarymotorsport_app.view.SupportScreen
import com.example.legendarymotorsport_app.view.MapScreen
import com.example.legendarymotorsport_app.view.CartScreen
import com.example.legendarymotorsport_app.view.FavoritesScreen
import com.example.legendarymotorsport_app.view.OrdersScreen
import com.example.legendarymotorsport_app.view.GarageScreen
import com.example.legendarymotorsport_app.view.RatingsScreen

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AppContent() {
    var currentScreen by remember { mutableStateOf("login") }
    var users by remember { mutableStateOf(mutableListOf(User("admin", "123", "admin"))) }
    var currentUser by remember { mutableStateOf<User?>(null) }

    // Scope y estado del drawer compartido
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    when (currentScreen) {
        "login" -> LoginScreen(
            onLogin = { username, password ->
                val user = users.find { it.username == username && it.password == password }
                if (user != null) {
                    currentUser = user
                    currentScreen = "catalog"
                }
            },
            onGoToRegister = { currentScreen = "register" }
        )
        "register" -> RegisterScreen(
            onRegister = { username, password ->
                if (users.any { it.username == username }) false
                else {
                    users.add(User(username, password, "user"))
                    currentScreen = "login"
                    true
                }
            },
            onGoToLogin = { currentScreen = "login" }
        )
        "catalog" -> CatalogScreen(
            username = currentUser?.username ?: "",
            onLogout = {
                currentUser = null
                currentScreen = "login"
            },
            onNavigate = { currentScreen = it }
        )
        "cart" -> CartScreen(
            scope = scope,
            drawerState = drawerState,
            onLogout = {
                currentUser = null
                currentScreen = "login"
            },
            onNavigate = { currentScreen = it }
        )
        "support" -> SupportScreen(
            onLogout = {
                currentUser = null
                currentScreen = "login"
            },
            onNavigate = { currentScreen = it }
        )
        "map" -> MapScreen(
            onLogout = {
                currentUser = null
                currentScreen = "login"
            },
            onNavigate = { currentScreen = it }
        )

        "favorites" -> FavoritesScreen(
            onBack = { currentScreen = "catalog" }
        )

        "orders" -> OrdersScreen(
            onBack = { currentScreen = "catalog" }
        )

        "garage" -> GarageScreen(
            onBack = { currentScreen = "catalog" }
        )

        "ratings" -> RatingsScreen(
            onBack = { currentScreen = "catalog" }
        )
    }
}
