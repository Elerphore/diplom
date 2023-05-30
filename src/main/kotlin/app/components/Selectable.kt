package app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import state.ApplicationState
import utils.TableType

@Composable
fun groupReportSelectable(groupName: String, onGroupNameChange: (String) -> Unit) =
    Row(Modifier.fillMaxWidth(0.9F), Arrangement.spacedBy(10.dp), Alignment.CenterVertically) {
        var expanded by remember { mutableStateOf(false) }

        var options by remember { mutableStateOf(listOf<String?>()) }

        options = ApplicationState.groupNames

        TextField(
            label = { Text("Группа") },
            modifier = Modifier.fillMaxWidth().onFocusChanged { expanded = it.hasFocus },
            value = groupName,
            onValueChange = onGroupNameChange,
            singleLine = true,
            readOnly = true,
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, content = {
            if (options.isNotEmpty()) {
                options.forEachIndexed { index, s ->
                    DropdownMenuItem(content = { Text(s!!) }, onClick = { onGroupNameChange(options[index]!!) })
                }
            }
        })

    }

@Composable
fun typeReportSelectable(placeholder: String, onTextFieldChange: (String) -> Unit) =
    Row(Modifier.fillMaxWidth(0.9F), Arrangement.spacedBy(10.dp), Alignment.CenterVertically) {
        var expanded by remember { mutableStateOf(false) }

        val options = listOf<String>()

        TextField(
            label = { Text("Тип приказа") },
            modifier = Modifier.fillMaxWidth().onFocusChanged { expanded = it.hasFocus },
            value = placeholder,
            onValueChange = onTextFieldChange,
            readOnly = true,
            singleLine = true,
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, content = {
            options.forEachIndexed { index, s ->
                DropdownMenuItem(content = { Text(s) }, onClick = { onTextFieldChange(options[index]) })
            }
        })

    }

@Composable
fun tableType() = Row(Modifier.fillMaxWidth(0.9F), Arrangement.spacedBy(10.dp), Alignment.CenterVertically) {

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {

        Text(TableType.values()[selectedIndex].stringName, modifier = Modifier.fillMaxWidth().clickable(onClick = { expanded = true }))

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded }, modifier = Modifier.fillMaxWidth()
        ) {
            TableType.values().forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {

                    selectedIndex = index
                    expanded = false
                    ApplicationState.selectedType = TableType.values()[selectedIndex]

                }) {
                    Text(text = s.stringName)
                }
            }
        }
    }
}