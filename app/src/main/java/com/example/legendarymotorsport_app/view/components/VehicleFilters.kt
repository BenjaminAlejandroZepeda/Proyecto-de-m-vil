package com.example.legendarymotorsport_app.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.legendarymotorsport_app.model.Vehicle

@Composable
fun VehicleFilters(
    vehicles: List<Vehicle>,
    onFilter: (FilterCriteria) -> Unit
) {

    var searchTerm by remember { mutableStateOf("") }
    var selectedManufacturers by remember { mutableStateOf(setOf<String>()) }

    val manufacturers = remember(vehicles) {
        vehicles.map { it.manufacturer }.toSet().sorted()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Filtros", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            // Buscar por nombre
            OutlinedTextField(
                value = searchTerm,
                onValueChange = { searchTerm = it },
                label = { Text("Buscar por nombre o modelo") },
                placeholder = { Text("Ej: Zentorno") },
                modifier = Modifier.fillMaxWidth()
            )


            // Fabricantes
            Text("Fabricantes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
            LazyColumn(
                modifier = Modifier
                    .heightIn(max = 150.dp)
                    .padding(vertical = 4.dp)
            ) {
                items(manufacturers.size) { index ->
                    val m = manufacturers[index]
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = selectedManufacturers.contains(m),
                                onValueChange = {
                                    selectedManufacturers =
                                        if (it) selectedManufacturers + m else selectedManufacturers - m
                                }
                            )
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = selectedManufacturers.contains(m),
                            onCheckedChange = null
                        )
                        Text(m.uppercase(), modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

        }
    }
}

data class FilterCriteria(
    val minPrice: Int = 0,
    val maxPrice: Int = Int.MAX_VALUE,
    val manufacturers: Set<String> = emptySet(),
    val searchTerm: String = ""
)
