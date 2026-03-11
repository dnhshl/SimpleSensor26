package com.example.main.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.example.main.R
import kotlinx.serialization.Serializable

/**
 * 1. THE SCREEN IDs
 * 
 * Every screen needs a unique ID (the name of the object).
 * These IDs are used to tell the app where to navigate.
 */
@Serializable
sealed interface Screen : NavKey {
    @Serializable data object Home : Screen
    @Serializable data object Sensors : Screen
    @Serializable data object Settings : Screen
    
    // Screens for the multi-step process
    @Serializable data object Step1 : Screen
    @Serializable data object Step2 : Screen
    @Serializable data object Step3 : Screen
}

/**
 * 2. THE CENTRAL CONFIGURATION
 * 
 * This is the "Master List" for your screens. 
 * Everything about a screen (title, icons, tabs) is defined here in one place.
 */
data class ScreenConfig(
    val screen: Screen,
    @StringRes val titleRes: Int,
    val showBottomBar: Boolean = true,
    @StringRes val label: Int? = null,    // Set this only for main navigation tabs
    val activeIcon: ImageVector? = null,  // Set this only for main navigation tabs
    val inactiveIcon: ImageVector? = null // Set this only for main navigation tabs
)

val AppScreenConfigs = listOf(
    ScreenConfig(
        screen = Screen.Home,
        titleRes = R.string.title_home,
        label = R.string.tab_home,
        activeIcon = Icons.Filled.Home,
        inactiveIcon = Icons.Outlined.Home
    ),
    ScreenConfig(
        screen = Screen.Sensors,
        titleRes = R.string.title_sensors,
        label = R.string.tab_sensors,
        activeIcon = Icons.Filled.Sensors,
        inactiveIcon = Icons.Outlined.Sensors
    ),
    ScreenConfig(
        screen = Screen.Settings,
        titleRes = R.string.title_settings,
        label = R.string.tab_settings,
        activeIcon = Icons.Filled.Settings,
        inactiveIcon = Icons.Outlined.Settings
    ),
    ScreenConfig(
        screen = Screen.Step1,
        titleRes = R.string.title_step1,
        showBottomBar = false // Hides the bottom menu for this screen
    ),
    ScreenConfig(
        screen = Screen.Step2,
        titleRes = R.string.title_step2,
        showBottomBar = false
    ),
    ScreenConfig(
        screen = Screen.Step3,
        titleRes = R.string.title_step3,
        showBottomBar = false
    )
)

/**
 * 3. HELPER FUNCTIONS
 * 
 * These allow you to easily access the configuration of any screen ID.
 * Example: 'Screen.Home.titleRes' will give you R.string.title_home.
 */
val Screen.config: ScreenConfig
    get() = AppScreenConfigs.first { it.screen == this }

val Screen.titleRes: Int get() = config.titleRes
val Screen.showBottomBar: Boolean get() = config.showBottomBar

/** 
 * This list is used to build the bottom navigation bar.
 * It automatically includes all screens that have a 'label' defined above.
 */
val MainTabs = AppScreenConfigs.filter { it.label != null }
