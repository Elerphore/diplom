package app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.components.button
import app.components.input
import state.ApplicationState

class AuthScreen : ScreenInterface {
    @Composable
    override fun window() =
        Column(
            Modifier.fillMaxWidth().padding(top = 30.dp).verticalScroll(rememberScrollState()),
            Arrangement.spacedBy(10.dp),
            Alignment.CenterHorizontally
        ) {
            Text(text = "Авторизация", fontSize = 26.sp, fontWeight = W500)

            var name by remember { mutableStateOf("") }
            input("Имя", name) {
                ApplicationState.username = it
                name = it
            }

            var password by remember { mutableStateOf("") }
            input("Пароль", password, true) {
                ApplicationState.password = it
                password = it
            }

            button("Войти") { ApplicationState.auth() }
        }
}