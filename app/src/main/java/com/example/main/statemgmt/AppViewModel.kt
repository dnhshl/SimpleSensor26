package com.example.main.statemgmt

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.main.ui.screens.Screen
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * The Central Control Center (ViewModel) of your App.
 * 
 * This is where you manage your data and logic.
 * Think of it like the "loop" and global variables in an Arduino sketch.
 */
class AppViewModel(application: Application, private val savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    private val dataStoreManager = DataStoreManager(application)

    // --- 1. UI STATE (Memory only) ---
    // These variables survive screen rotations but are lost when the app is fully closed.
    // To add more, edit the 'UiState' class in UiState.kt.
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // --- 2. PERSISTENT STATE (Disk storage) ---
    // These variables are saved to the phone's memory and survive app restarts.
    // To add more, edit the 'PersistantUiState' class in UiState.kt.
    val persistantUiState: StateFlow<PersistantUiState> = dataStoreManager.persistantUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PersistantUiState()
        )

    // --- 3. LOGIC & ACTIONS ---
    // Change or add functions here to handle button clicks or sensor updates.

    /** To toggle the LED state (On/Off) */
    fun toggleLed() {
        _uiState.update { it.copy(isLedOn = !it.isLedOn) }
    }

    /** To update the current temperature display */
    fun updateTemperature(newTemp: Float) {
        _uiState.update { it.copy(temperature = newTemp) }
    }

    /** To increment the persistent click counter */
    fun incrementClicks() {
        viewModelScope.launch {
            dataStoreManager.updatePersistantState { it.copy(numClicks = it.numClicks + 1) }
        }
    }

    /** To change the persistent LED color */
    fun updateColor(newColor: Color) {
        viewModelScope.launch {
            dataStoreManager.updatePersistantState { it.copy(myColor = newColor) }
        }
    }

    /** To add a new item to the persistent sensor list */
    fun addSensorItem(item: String) {
        if (item.isBlank()) return
        viewModelScope.launch {
            dataStoreManager.updatePersistantState { current ->
                current.copy(sensorItems = current.sensorItems + item)
            }
        }
    }

    /** To remove an item from the persistent sensor list */
    fun removeSensorItem(item: String) {
        viewModelScope.launch {
            dataStoreManager.updatePersistantState { current ->
                current.copy(sensorItems = current.sensorItems - item)
            }
        }
    }


    // =========================================================================
    // BOILERPLATE AREA: NAVIGATION HANDLING
    // You usually don't need to change anything below this line.
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

    /** Switches to a main tab (clears history) */
    fun navigateTo(screen: Screen) {
        if (backStack.lastOrNull() != screen) {
            backStack.clear()
            backStack.add(screen)
            saveBackStack()
        }
    }

    /** Pushes a new screen onto the stack (like a sub-page) */
    fun push(screen: Screen) {
        backStack.add(screen)
        saveBackStack()
    }

    /** Navigates back one screen */
    fun popBackStack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
            saveBackStack()
        }
    }
}
