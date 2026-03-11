package com.example.main.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.main.R
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * A Generic Permission Wrapper for Educational Projects.
 * 
 * Think of this as a "Gatekeeper". It checks if the app has the "key" (permission) 
 * to use a hardware feature. If not, it shows a button to ask the user.
 * 
 * HOW TO USE:
 * PermissionWrapper(
 *     permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
 *     content = { /* Your Screen Content goes here */ }
 * )
 */
@Composable
fun PermissionWrapper(
    permission: String,
    title: String = stringResource(R.string.permission_required_title),
    description: String = stringResource(R.string.permission_required_msg),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    
    // State to track if permission is granted
    var isGranted by remember { 
        mutableStateOf(checkPermission(context, permission)) 
    }

    // This is the "System Dialog" launcher (the popup that says "Allow app to access...")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { result ->
        isGranted = result
    }

    if (isGranted) {
        // If we have permission, show the actual screen content
        content()
    } else {
        // If not, show an "Access Denied" or "Request Access" UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { launcher.launch(permission) }) {
                Text(stringResource(R.string.btn_grant_permission))
            }
        }
    }
}

/** Helper function to check permission status */
private fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context, 
        permission
    ) == PackageManager.PERMISSION_GRANTED
}
