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
 */
@Serializable
sealed interface Screen : NavKey {
    @Serializable data object Home : Screen
    @Serializable data object Gyro : Screen
    @Serializable data object Gps : Screen
}

/**
 * 2. THE CENTRAL CONFIGURATION
 */
data class ScreenConfig(
    val screen: Screen,
    @StringRes val titleRes: Int,
    val showBottomBar: Boolean = true,
    @StringRes val label: Int? = null,
    val activeIcon: ImageVector? = null,
    val inactiveIcon: ImageVector? = null
)

val AppScreenConfigs = listOf(
    ScreenConfig(
        screen = Screen.Home,
        titleRes = R.string.title_home,
        label = R.string.tab_home,
        activeIcon = Icons.Filled.Sensors,
        inactiveIcon = Icons.Outlined.Sensors
    ),
    ScreenConfig(
        screen = Screen.Gyro,
        titleRes = R.string.title_gyro,
        label = R.string.tab_gyro,
        activeIcon = Icons.Filled.Explore,
        inactiveIcon = Icons.Outlined.Explore
    ),
    ScreenConfig(
        screen = Screen.Gps,
        titleRes = R.string.title_gps,
        label = R.string.tab_gps,
        activeIcon = Icons.Filled.LocationOn,
        inactiveIcon = Icons.Outlined.LocationOn
    )
)

val Screen.config: ScreenConfig
    get() = AppScreenConfigs.first { it.screen == this }

val Screen.titleRes: Int get() = config.titleRes
val Screen.showBottomBar: Boolean get() = config.showBottomBar

val MainTabs = AppScreenConfigs.filter { it.label != null }
