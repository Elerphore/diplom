package app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.components.renderButton
import app.components.renderMainInputs
import app.components.renderReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import state.ApplicationState
import java.io.File

fun app() = application {

    CoroutineScope(Dispatchers.IO).launch {
        ApplicationState.init()
    }

    Window(title = "Отчётная система МПК", onCloseRequest = ::exitApplication) {
        MaterialTheme {
            renderMain()
        }
    }
}

@Composable
fun renderMain() {
    var dataFile by remember { mutableStateOf(File("")) }
    var showReport by remember { mutableStateOf(false) }
    val onChangeReport: (Boolean) -> Unit = { showReport = it }

    var text by remember { mutableStateOf("") }
    val onChangeText: (String) -> Unit = { text = it }

    Column(
        Modifier.fillMaxWidth().padding(top = 30.dp).verticalScroll(rememberScrollState()),
        Arrangement.spacedBy(10.dp),
        Alignment.CenterHorizontally
    ) {
        renderMainInputs(
            showReport = showReport,
            onChangeReport = onChangeReport,
        )

        if (showReport) {
            renderReport(text, onChangeText)
        }

        renderButton(dataFile)
    }
}