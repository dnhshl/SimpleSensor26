package com.example.main.ui.screens

import com.example.main.statemgmt.AppViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * The Home Screen of your app.
 * It demonstrates how to observe both temporary and persistent data.
 */
@Composable
fun HomeScreen(viewModel: AppViewModel) {
    // We observe both states: persistent (DataStore) and temporary (ViewModel memory)
    val state by viewModel.uiState.collectAsState()
    val persistentState by viewModel.persistantUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.home_welcome),
            style = MaterialTheme.typography.headlineSmall
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        // The LED: A colored box
        // If 'isLedOn' is false, we show a dark gray (off state)
        // If it is true, we use the 'myColor' stored in the persistent state
        val ledColor = if (state.isLedOn) persistentState.myColor else Color.DarkGray
        
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ledColor)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // This button triggers an action in the ViewModel to toggle the LED
        Button(onClick = { viewModel.toggleLed() }) {
            Text(
                text = if (state.isLedOn) stringResource(R.string.led_on) else stringResource(R.string.led_off)
            )
        }
    }
}
