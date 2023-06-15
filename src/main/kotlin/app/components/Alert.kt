package app.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object Alert {
    var isShowDialog: MutableState<Boolean>? = null

    var description: String = ""
    var dismiss: String = "Понял"
    var confirm: String? = null
    val title: String = "Внимание!"

    var onConfirm: () -> Unit? = { }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun alert(): Unit? {
        if (isShowDialog == null) {
            isShowDialog = mutableStateOf(false)
        }

        if (isShowDialog?.value == true) {

            return AlertDialog(onDismissRequest = { isShowDialog?.value = false },
                modifier = Modifier.size(325.dp,150.dp),
                title = { Text(title) },
                confirmButton = {
                    confirm?.let {
                        Button(onClick = { onConfirm() }) { Text(it) }
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        isShowDialog?.value = false
                    }) {

                        Text(dismiss)
                    }
                },
                text = { Text(description) })
        } else {
            return null
        }
    }

    @Composable
    fun showAlert() {
        if (isShowDialog == null) {
            isShowDialog = mutableStateOf(true)
        }

        isShowDialog?.value = true
    }

    @Composable
    fun hideAlert() {
        if (isShowDialog == null) {
            isShowDialog = mutableStateOf(false)
        }

        isShowDialog?.value = false
    }
}