package com.example.main.statemgmt

import android.app.Application
import android.hardware.Sensor
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.main.repositories.LocationRepository
import com.example.main.repositories.SensorRepository
import com.example.main.ui.screens.Screen
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * The Central Control Center (ViewModel) of your App.
 */
class AppViewModel(application: Application, private val savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    private val dataStoreManager = DataStoreManager(application)
    private val sensorRepository = SensorRepository(application)
    private val locationRepository = LocationRepository(application)

    // --- 1. UI STATE (Memory only) ---
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // --- 2. PERSISTENT STATE (Disk storage) ---
    val persistantUiState: StateFlow<PersistantUiState> = dataStoreManager.persistantUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PersistantUiState()
        )

    init {
        // Load the list of available sensors on start
        _uiState.update { it.copy(availableSensors = sensorRepository.getAllSensors()) }

        // Start listening to Accelerometer
        viewModelScope.launch {
            sensorRepository.getSensorUpdates(Sensor.TYPE_ACCELEROMETER)
                .collect { event ->
                    _uiState.update { it.copy(
                        accelX = event.values[0],
                        accelY = event.values[1],
                        accelZ = event.values[2]
                    )}
                }
        }

        // Start listening to Gyroscope
        viewModelScope.launch {
            sensorRepository.getSensorUpdates(Sensor.TYPE_GYROSCOPE)
                .collect { event ->
                    _uiState.update { it.copy(
                        gyroX = event.values[0],
                        gyroY = event.values[1],
                        gyroZ = event.values[2]
                    )}
                }
        }

        // Start listening to GPS Location
        viewModelScope.launch {
            locationRepository.getLocationUpdates()
                .collect { location ->
                    _uiState.update { it.copy(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )}
                }
        }
    }

    // =========================================================================
    // BOILERPLATE AREA: NAVIGATION HANDLING
    // =========================================================================

    private val savedBackStack: List<Screen>? = savedStateHandle.get<String>("backstack_keys")?.let {
        try { Json.decodeFromString<List<Screen>>(it) } catch (e: Exception) { null }
    }
    
    val backStack = mutableStateListOf<Screen>().apply {
        addAll(savedBackStack ?: listOf(Screen.Home))
    }

    private fun saveBackStack() {
        savedStateHandle["backstack_keys"] = Json.encodeToString(backStack.toList())
    }

    fun navigateTo(screen: Screen) {
        if (backStack.lastOrNull() != screen) {
            backStack.clear()
            backStack.add(screen)
            saveBackStack()
        }
    }

    fun popBackStack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
            saveBackStack()
        }
    }
}
