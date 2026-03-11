package com.example.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.main.R
import com.example.main.statemgmt.AppViewModel

/**
 * Gyro Screen.
 * Displays real-time data from the Accelerometer and Gyroscope.
 */
@Composable
fun GyroScreen(viewModel: AppViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
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

@Composable
fun SensorDataCard(title: String, x: Float, y: Float, z: Float) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
