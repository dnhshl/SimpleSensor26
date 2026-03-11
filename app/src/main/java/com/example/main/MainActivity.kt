package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.main.statemgmt.AppViewModel
import com.example.main.ui.screens.*
import kotlin.getValue

/**
 * The main Activity is the entry point of your Android app.
 * It sets up the basic layout and navigation.
 */
class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainAppScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModel: AppViewModel) {
    // Determine the current screen from the backstack
    val currentScreen = viewModel.backStack.lastOrNull() ?: Screen.Home

    // Handle the system back button
    BackHandler(enabled = viewModel.backStack.size > 1) {
        viewModel.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(currentScreen.titleRes)) },
                navigationIcon = {
                    // Show a back button only if the bottom bar is hidden (e.g., in a sub-screen)
                    if (!currentScreen.showBottomBar) {
                        IconButton(onClick = { viewModel.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_description)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            // Show the bottom navigation bar only if the current screen allows it
            if (currentScreen.showBottomBar) {
                NavigationBar {
                    MainTabs.forEach { config ->
                        NavigationBarItem(
                            selected = currentScreen == config.screen,
                            onClick = {
                                viewModel.navigateTo(config.screen)
                            },
                            label = { config.label?.let { Text(stringResource(it)) } },
                            icon = {
                                val icon = if (currentScreen == config.screen) config.activeIcon else config.inactiveIcon
                                if (icon != null) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = config.label?.let { stringResource(it) }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // The NavDisplay is responsible for showing the actual content of the current screen
            NavDisplay(
                backStack = viewModel.backStack,
                onBack = { viewModel.popBackStack() },
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                )
            ) { screen ->
                NavEntry(screen) {
                    // To add a new screen, create a new case here
                    when (screen) {
                        is Screen.Home -> HomeScreen(viewModel)
                        is Screen.Sensors -> SensorScreen(viewModel)
                        is Screen.Settings -> SettingsScreen(viewModel)
                        is Screen.Step1 -> Step1Screen(viewModel)
                        is Screen.Step2 -> Step2Screen(viewModel)
                        is Screen.Step3 -> Step3Screen(viewModel)
                    }
                }
            }
        }
    }
}
