package com.example.main.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.main.R
import com.example.main.statemgmt.AppViewModel
import com.example.main.statemgmt.UiState
import com.example.main.ui.components.PermissionWrapper

/**
 * 1. THE SCREEN WRAPPER
 * 
 * Handles the "Gatekeeping" (Permissions) and "Data Fetching" (ViewModel).
 */
@Composable
fun GpsScreen(viewModel: AppViewModel) {
    val state by viewModel.uiState.collectAsState()

    // We check for permission first
    PermissionWrapper(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        description = stringResource(R.string.permission_required_msg)
    ) {
        // If granted, we show the actual screen content
        GpsContent(state = state)
    }
}

/**
 * 2. THE CONTENT VIEW
 * 
 * Pure UI that displays the active GPS data.
 */
@Composable
fun GpsContent(state: UiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(R.string.label_gps_active),
            style = MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.label_latitude, state.latitude),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.label_longitude, state.longitude),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
