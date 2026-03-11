package com.example.main.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.main.R

/**
 * A reusable Dialog component to adjust a temperature value.
 * 
 * To use this dialog:
 * 1. Pass the current temperature as [initialTemperature].
 * 2. Define what happens when the user cancels ([onDismiss]).
 * 3. Define what happens when the user saves a new value ([onConfirm]).
 */
@Composable
fun TemperatureDialog(
    initialTemperature: Float,
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit
) {
    // We keep the text input as a String locally while the user is typing.
    var tempInput by remember { mutableStateOf(initialTemperature.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.dialog_title_temp)) },
        text = {
            Column {
                Text(stringResource(R.string.dialog_msg_temp))
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = tempInput,
                    onValueChange = { tempInput = it },
                    singleLine = true,
                    label = { Text(stringResource(R.string.label_temp)) },
                    // This tells the phone to show a numeric keyboard with a decimal point.
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // When saving, we try to convert the text back to a Float.
                // If the input is invalid (e.g., empty or just a dot), we keep the old value.
                val newTemp = tempInput.toFloatOrNull() ?: initialTemperature
                onConfirm(newTemp)
            }) {
                Text(stringResource(R.string.btn_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.btn_cancel))
            }
        }
    )
}
