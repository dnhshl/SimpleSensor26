package com.example.main.ui.screens

import com.example.main.statemgmt.AppViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.main.R

/**
 * The Settings Screen.
 * It demonstrates how to update persistent data (like the LED color)
 * and how to trigger a linear navigation chain.
 */
@Composable
fun SettingsScreen(viewModel: AppViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    
    // We observe the persistent state to know which color is currently selected
    val persistentState by viewModel.persistantUiState.collectAsState()

    // A list of colors to choose from
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta, Color.Cyan)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.settings_header), style = MaterialTheme.typography.headlineSmall)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Color selection for the LED
            Text(text = stringResource(R.string.label_select_color), style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { viewModel.updateColor(color) } // Update the persistent color in ViewModel
                            .padding(4.dp)
                    ) {
                        // Show a small marker if this color is currently selected
                        if (persistentState.myColor == color) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.5f))
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // Button to open the temperature adjustment dialog
            Button(onClick = { showDialog = true }) {
                Text(stringResource(R.string.btn_set_temp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Button to start the multi-step navigation chain
            Button(onClick = { viewModel.push(Screen.Step1) }) {
                Text(stringResource(R.string.btn_start_chain))
            }
        }
    }

    // Logic to show the custom AlertDialog
    if (showDialog) {
        val state by viewModel.uiState.collectAsState()
        TemperatureDialog(
            initialTemperature = state.temperature,
            onDismiss = { showDialog = false },
            onConfirm = { newTemp ->
                viewModel.updateTemperature(newTemp)
                showDialog = false
            }
        )
    }
}
