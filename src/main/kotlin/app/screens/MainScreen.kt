package app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.components.button
import app.components.tableType

class MainScreen: ScreenInterface {
    @Composable
    override fun window() =
        Column(
            Modifier.fillMaxWidth().padding(top = 30.dp).verticalScroll(rememberScrollState()),
            Arrangement.spacedBy(10.dp),
            Alignment.CenterHorizontally
        ) {
            tableType()
            button("Сгенерировать файл")
        }
}