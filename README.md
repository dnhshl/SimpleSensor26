# 📱 SimpleSensor Educational App

Welcome to the **SimpleSensor** project! This is a specialized version of our standard Android Starter Template, designed specifically for your first steps into **Mobile Sensing** and **Hardware Interaction**.

## 🎓 The "Discovery" Approach
Instead of building this app step-by-step, we are using a **"Reverse Engineering"** approach:
1.  **Explore**: You receive a fully functional app that talks to your phone's hardware.
2.  **Understand**: Your goal is to trace the data from the hardware sensor, through the Repository and ViewModel, and finally to the UI.
3.  **Modify**: Once you understand the flow, your task will be to add new sensors (e.g., Light, Pressure) or expand the GPS data (e.g., Altitude, Speed).

---

## 🏗️ Architecture: The Data Pipeline
To keep the code clean and professional, we follow a simple 3-layer pipeline. Think of it like an Arduino project with a specialized library:

### 1. The Repository (The "Driver")
*   **Location**: `com.example.main.repositories`
*   **Purpose**: This layer talks directly to the Android System and the hardware. 
*   **Concept**: It provides a **`Flow`** of data. A `Flow` is like a continuous stream—every time the sensor moves, a new value is pushed into the stream.
*   **Key Files**: `SensorRepository.kt`, `LocationRepository.kt`.

### 2. The ViewModel (The "Brain")
*   **Location**: `com.example.main.statemgmt.AppViewModel`
*   **Purpose**: This is the central "Loop". It collects data from the Repositories and stores it in the `UiState`.
*   **Concept**: In the `init { ... }` block, we launch coroutines (background tasks) that "collect" the sensor streams and update our variables.

### 3. The UI (The "Display")
*   **Location**: `com.example.main.ui.screens`
*   **Purpose**: Purely for displaying data. 
*   **Concept**: We use a **"Wrapper" pattern**. 
    *   **Screen function**: Handles the connection to the ViewModel.
    *   **Content function**: A "pure" view that only knows how to draw a `UiState`.
    *   **PermissionWrapper**: A special gatekeeper for sensitive data like GPS.

---

## 🔒 Permission Management
For the first time, you will encounter **Android Permissions**. 
*   **Manifest**: We must declare our intent to use GPS in the `AndroidManifest.xml`.
*   **Just-In-Time**: We only ask the user for permission when they actually open the GPS tab.
*   **The Component**: Check out `PermissionWrapper.kt` to see how we handle the "Allow/Deny" dialog automatically.

---

## 🛠️ Your Tasks

### Task 1: Add a new Hardware Sensor
Currently, we read the Accelerometer and Gyroscope. 
1.  Open `UiState.kt` and add a new variable (e.g., `val lightLevel: Float = 0f`).
2.  In `AppViewModel.kt`, start collecting `Sensor.TYPE_LIGHT` from the repository.
3.  Display the value in the `HomeScreen.kt`.

### Task 2: Expand GPS Data
The `Location` object contains more than just Latitude and Longitude.
1.  Can you find out how to get the **Altitude** (Height) or the **Speed**?
2.  Add these to the `UiState` and display them in the `GpsScreen`.

### Task 3: Visual Feedback
In `GyroScreen.kt`, we change the background color based on tilt. 
1.  Try to change the color based on the **Z-Axis** instead.
2.  Can you make a "Level" (Wasserwaage) indicator using a `Box` that moves across the screen?

---

## 📁 Project Quick-Link
*   **Logic**: `AppViewModel.kt` -> Where the data is gathered.
*   **Data Structure**: `UiState.kt` -> Where the data is defined.
*   **Hardware Control**: `repositories/` -> Where the hardware is accessed.
*   **UI Layout**: `ui/screens/` -> Where the screens are designed.

---

## ⚖️ License
This project is licensed under the **MIT License**. You are free to use, modify, and distribute this code in your own projects. See the `LICENSE` file for details.
