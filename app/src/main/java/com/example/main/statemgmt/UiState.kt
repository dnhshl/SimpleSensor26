package com.example.main.statemgmt

import android.hardware.Sensor
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

/**
 * 1. UI STATE (Volatile)
 */
data class UiState(
    // Home Screen
    val availableSensors: List<Sensor> = emptyList(),
    
    // Gyro & Accel Screen
    val accelX: Float = 0f,
    val accelY: Float = 0f,
    val accelZ: Float = 0f,
    val gyroX: Float = 0f,
    val gyroY: Float = 0f,
    val gyroZ: Float = 0f,

    // GPS Screen
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

/**
 * 2. PERSISTENT UI STATE (Saved to Disk)
 */
@Serializable
data class PersistantUiState(
    // Keep the structure, but we don't need the template data anymore
    val lastUsedTimestamp: Long = 0L
)
