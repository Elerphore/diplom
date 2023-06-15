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
import app.components.Alert
import app.components.button
import app.components.tableType
import generator.generateTable
import state.ApplicationState
import utils.ScreenType
import utils.TableType

class MainScreen: ScreenInterface {
    @Composable
    override fun window() =
        Column(
            Modifier.fillMaxWidth().padding(top = 30.dp).verticalScroll(rememberScrollState()),
            Arrangement.spacedBy(10.dp),
            Alignment.CenterHorizontally
        ) {
            tableType()

            button("Сгенерировать файл") {

                if(ApplicationState.selectedType == TableType.NOT_SELECTED) {
                    Alert.description = "Выберите таблицу"
                    Alert.dismiss = "Понял"
                } else {
                    Alert.description =
                        "Вы уверены что хотите сгенерировать таблицу \"${ApplicationState.selectedType?.stringName}\"?"

                    Alert.dismiss = "Отменить"

                    Alert.confirm = "Продолжить"

                    Alert.onConfirm = {
                        Alert.isShowDialog?.value = false
                        generateTable()
                    }
                }

                Alert.isShowDialog?.value = true

            }

            button("Выйти") {

                Alert.description = "Вы уверены что хотите выйти?"
                Alert.dismiss = "Отмена"

                Alert.confirm = "Продолжить"
                Alert.onConfirm = {
                    Alert.isShowDialog?.value = false
                    ApplicationState.logout()
                }

                Alert.isShowDialog?.value = true

            }

        }
}