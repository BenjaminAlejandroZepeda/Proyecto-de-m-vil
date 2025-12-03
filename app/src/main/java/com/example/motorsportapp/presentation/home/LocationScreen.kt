package com.example.motorsportapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.motorsportapp.R
import com.example.motorsportapp.domain.model.loadDealerships
import com.example.motorsportapp.domain.model.isOpenNow
import com.example.motorsportapp.domain.model.OpenMapsButton
import com.example.motorsportapp.presentation.ui.BottomNavBar
import com.example.motorsportapp.ui.theme.BackgroundAlt
import com.example.motorsportapp.ui.theme.TextPrimary
import com.example.motorsportapp.ui.theme.PrimaryColor
import com.example.motorsportapp.ui.theme.SuccessColor
import com.example.motorsportapp.ui.theme.ErrorColor

@Composable
fun LocationScreen(navController: NavHostController) {
    val dealerships = loadDealerships()

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        containerColor = BackgroundAlt
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dealerships) { dealer ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BackgroundAlt),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = dealer.imageUrl,
                            contentDescription = dealer.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            placeholder = painterResource(R.drawable.placeholder),
                            error = painterResource(R.drawable.image_error)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            dealer.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            dealer.address,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )

                        Text(
                            "Tel: ${dealer.contact}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Horario: ${dealer.openHour}:00 - ${dealer.closeHour}:00",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val isOpen = dealer.isOpenNow()
                            Text(
                                if (isOpen) "Abierto" else "Cerrado",
                                color = if (isOpen) SuccessColor else ErrorColor,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))


                        OpenMapsButton(
                            latitude = dealer.latitude,
                            longitude = dealer.longitude,
                            label = dealer.name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
