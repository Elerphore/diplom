package app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.components.button
import app.components.tableType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import state.ApplicationState

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
    Column(
        Modifier.fillMaxWidth().padding(top = 30.dp).verticalScroll(rememberScrollState()),
        Arrangement.spacedBy(10.dp),
        Alignment.CenterHorizontally
    ) {
        tableType()

        /* Generate excel table */
        button()
    }
}