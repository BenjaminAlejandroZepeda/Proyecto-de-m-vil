package com.example.motorsportapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.motorsportapp.domain.model.loadDealerships
import com.example.motorsportapp.presentation.ui.BottomNavBar
import androidx.navigation.NavHostController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import com.example.motorsportapp.R




@Composable
fun LocationScreen(navController: NavHostController) {
    val dealerships = loadDealerships()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            items(dealerships) { dealer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        AsyncImage(
                            model = dealer.imageUrl,
                            contentDescription = dealer.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            placeholder = painterResource(R.drawable.placeholder),
                            error = painterResource(R.drawable.image_error)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(dealer.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(dealer.address, style = MaterialTheme.typography.bodyMedium)
                        Text("Tel: ${dealer.contact}", style = MaterialTheme.typography.bodySmall)
                        Text("Horario: ${dealer.openHour}:00 - ${dealer.closeHour}:00", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
