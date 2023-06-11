package app

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.screens.AuthScreen
import app.screens.MainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import state.ApplicationState
import utils.ScreenType

fun app() = application {
    CoroutineScope(Dispatchers.IO).launch {
        ApplicationState.init()
    }

    Window(title = "Отчётная система МПК", onCloseRequest = ::exitApplication) {
        MaterialTheme {
            when(ApplicationState.screen) {
                ScreenType.AUTHORIZATION -> AuthScreen().window()
                ScreenType.MAIN -> MainScreen().window()
            }
        }
    }
}
