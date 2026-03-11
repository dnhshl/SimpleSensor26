# MultiScreenStarter App

This project is a starter template for Android applications built with Jetpack Compose. It is designed to help students learn how to manage navigation, state, and data persistence in a structured way.

## 🏁 How to start your own project from this template

To avoid the "it's too cumbersome to rename everything" hurdle, this template uses a generic package name: `com.example.main`. You can keep this package name in your code files to save time, and only change the "external" identity of your app.

### Scenario A: You are using GitHub
1.  **Use the Template**: Click the green **"Use this template"** button on GitHub (or **Fork** the repository).
2.  **Clone**: Clone your new repository to your computer.
3.  **Open**: Start Android Studio and select **"Open"**, then navigate to your cloned folder.

### Scenario B: You are not using Git (ZIP Download)
1.  **Download**: Click the **"Code"** button on GitHub and select **"Download ZIP"**.
2.  **Extract**: Unzip the file to a folder on your computer.
3.  **Open**: Start Android Studio and select **"Open"**, then navigate to your extracted folder.

### 🛠️ Required Adjustments (Do this first!)
Once the project is open in Android Studio, perform these two steps to make the app your own:
1.  **Change the Display Name**: 
    *   Open `app/src/main/res/values/strings.xml`.
    *   Change `<string name="app_name">MultiScreenStarter</string>` to your desired app name (e.g., `"MyAwesomeApp"`).
2.  **Change the Application ID**: 
    *   Open `app/build.gradle.kts` (the one inside the `app` folder).
    *   Find `applicationId = "com.example.main"`.
    *   Change it to something unique, like `"com.yourname.projectname"`. 
    *   *Note: This allows you to install your app alongside the original template on the same phone.*
3.  **Sync**: Click the **"Sync Now"** bar that appears at the top of the editor.

---

## 🚀 How to use this Starter Template

### 1. Adding a new Screen
To add a new screen to your app:
1.  **Create the UI**: Create a new file in `ui/screens/` (e.g., `MyNewScreen.kt`) and define a `@Composable` function that takes the `AppViewModel` as an argument.
2.  **Define the ID**: Open `AppConfig.kt` and add a new `object` to the `Screen` interface.
3.  **Configure it**: Add a `ScreenConfig` entry to the `AppScreenConfigs` list in `AppConfig.kt`. Here you decide:
    *   What the title in the Top Bar should be.
    *   If it should show the Bottom Navigation Bar (`showBottomBar`).
    *   If it should appear as a Tab in the Bottom Bar (by adding a `label` and `icons`).
4.  **Map the UI**: In `MainActivity.kt`, add your new screen to the `when` block inside `NavDisplay`.

### 2. Managing State (Data)
There are two ways to store data in this app, defined in `UiState.kt`:

*   **UiState (Volatile/Temporary)**: Use this for data that only matters during the current session (e.g., is a light currently on?). This data is lost when the app is closed.
    *   *To add a variable*: Add it to the `UiState` data class.
    *   *To update it*: Add a function in `AppViewModel.kt` that uses `_uiState.update { it.copy(...) }`.
*   **PersistantUiState (Permanent)**: Use this for data that should be remembered (e.g., user settings, high scores). This is saved to the phone's storage.
    *   *To add a variable*: Add it to the `PersistantUiState` data class.
    *   *To update it*: Add a function in `AppViewModel.kt` that uses `dataStoreManager.updatePersistantState { it.copy(...) }`.

### 3. Handling Navigation
Navigation is handled centrally in the `AppViewModel.kt`:
*   Use `navigateTo(Screen.YourScreen)` for main tabs (clears the back history).
*   Use `push(Screen.YourScreen)` for sub-pages or "chains" of screens.
*   The **System Back Button** is handled automatically in `MainActivity.kt`.

## 📁 Project Structure

*   `MainActivity.kt`: The main entry point. Handles the Scaffold (TopBar/BottomBar) and the navigation display.
*   `statemgmt/`:
    *   `AppViewModel.kt`: The "Brain" of your app. Write your logic and actions here.
    *   `UiState.kt`: Defines what data your app holds. Add your variables here.
    *   `DataStoreManager.kt` & `Helper.kt`: Handle background saving logic. **You don't need to touch these.**
*   `ui/screens/`: Contains all your UI files.
*   `res/values/strings.xml`: All text used in the app. **Never hardcode text in the UI; add it here instead.**
