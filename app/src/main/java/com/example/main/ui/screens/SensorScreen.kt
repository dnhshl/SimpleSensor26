package com.example.main.ui.screens

import com.example.main.statemgmt.AppViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.main.R

/**
 * The Sensor Screen.
 * It demonstrates how to display a simple value and a scrollable list of items.
 */
@Composable
fun SensorScreen(viewModel: AppViewModel) {
    val state by viewModel.uiState.collectAsState()
    val persistentState by viewModel.persistantUiState.collectAsState()
    
    // Local state for the text input field
    var newSensorName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Temperature Display
        Text(
            text = stringResource(R.string.label_current_temp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.temperature_display, state.temperature),
            style = MaterialTheme.typography.displayMedium
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))

        // 2. Sensor List Header
        Text(
            text = stringResource(R.string.label_active_sensors),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        // 3. Input area to add a new sensor
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newSensorName,
                onValueChange = { newSensorName = it },
                label = { Text(stringResource(R.string.hint_new_sensor)) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                viewModel.addSensorItem(newSensorName)
                newSensorName = "" // Clear the field after adding
            }) {
                Text(stringResource(R.string.btn_add_sensor))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. The list of sensors (using LazyColumn for performance)
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(persistentState.sensorItems) { sensor ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = sensor, modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.removeSensorItem(sensor) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.desc_remove_sensor),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
