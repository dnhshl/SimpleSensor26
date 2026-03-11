package com.example.main.statemgmt

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

/**
 * 1. UI STATE (Volatile)
 * 
 * These variables are kept in memory. They survive if you rotate the phone,
 * but they are RESET to their default values if the app is fully closed and restarted.
 * 
 * To add a new "temporary" variable, add it to this data class.
 */
data class UiState(
    val selectedTabIndex: Int = 0,
    val temperature: Float = 23.5f,
    val isLedOn: Boolean = false
)

/**
 * 2. PERSISTENT UI STATE (Saved to Disk)
 * 
 * These variables are saved in the phone's storage. They survive app restarts
 * and even phone reboots.
 * 
 * To add a new "permanent" variable, add it here.
 * If you use a complex type (like Color), you must specify a serializer.
 */
@Serializable
data class PersistantUiState(
    val numClicks: Int = 0,
    @Serializable(with = ColorSerializer::class)  // Custom Serializer defined in Helper.kt
    val myColor: Color = Color.Blue,
    val sensorItems: List<String> = listOf("Helligkeit", "Luftfeuchtigkeit")
)
