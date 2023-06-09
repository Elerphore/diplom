package app

import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.components.Alert
import app.screens.AuthScreen
import app.screens.MainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import state.ApplicationState
import utils.ScreenType

fun app() = application {
    Window(title = "Отчётная система МПК", onCloseRequest = ::exitApplication) {
        MaterialTheme {
            ApplicationState.screenTypeRendering(mutableStateOf(ApplicationState.screen))

            when(ApplicationState.screenTypeRender?.value) {
                ScreenType.AUTHORIZATION -> AuthScreen().window()
                ScreenType.MAIN -> MainScreen().window()
            }

            Alert.alert()

        }
    }
}
