package com.example.main.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.main.R
import com.example.main.statemgmt.AppViewModel
import com.example.main.statemgmt.UiState

/**
 * 1. THE SCREEN WRAPPER
 * 
 * This part connects the screen to the ViewModel.
 * It's responsible for "listening" to the data.
 */
@Composable
fun GyroScreen(viewModel: AppViewModel) {
    val state by viewModel.uiState.collectAsState()
    
    // We pass the data down to the content composable
    GyroContent(state = state)
}

/**
 * 2. THE CONTENT VIEW
 * 
 * This is the pure UI. It doesn't know about the ViewModel.
 * It only knows HOW to display a 'UiState' object.
 */
@Composable
fun GyroContent(state: UiState) {
    // We calculate a background color based on the tilt (accelX).
    val backgroundColor by animateColorAsState(
        targetValue = when {
            state.accelX > 3f -> Color(0xFF4CAF50).copy(alpha = 0.2f) // Tilted right -> Greenish
            state.accelX < -3f -> Color(0xFFF44336).copy(alpha = 0.2f) // Tilted left -> Reddish
            else -> MaterialTheme.colorScheme.background // Neutral
        },
        label = "bgColorAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Accelerometer Section
        SensorDataCard(
            title = stringResource(R.string.label_accelerometer),
            x = state.accelX,
            y = state.accelY,
            z = state.accelZ
        )

        // Gyroscope Section
        SensorDataCard(
            title = stringResource(R.string.label_gyroscope),
            x = state.gyroX,
            y = state.gyroY,
            z = state.gyroZ
        )
    }
}

/**
 * 3. REUSABLE COMPONENT
 * 
 * A simple card to display XYZ coordinates.
 */
@Composable
fun SensorDataCard(title: String, x: Float, y: Float, z: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.format_xyz, x, y, z),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
