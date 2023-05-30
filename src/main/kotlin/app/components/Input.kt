package app.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp

@Composable
fun renderReport(text: String, onTextFieldChange: (String) -> Unit) {
    var reportCount by remember { mutableStateOf(2) }

    Text("Количество приказов: $reportCount")

    LazyColumn(
        modifier = Modifier.height(320.dp),
    ) {
        items(reportCount) {
            var groupName by remember { mutableStateOf("") }
            val onGroupNameChange: (String) -> Unit = { newName -> groupName = newName }

            var studentName by remember { mutableStateOf("") }
            val onStudentNameChange: (String) -> Unit = { newName -> studentName = newName }

            Text("${it + 1} приказ")
            typeReportSelectable(placeholder = text, onTextFieldChange = onTextFieldChange)
            groupReportSelectable(groupName, onGroupNameChange)
            studentNameField(studentName, onStudentNameChange)
            Spacer(modifier = Modifier.height(20.0.dp))
        }
    }

    Button(
        onClick = { reportCount++; },
        modifier = Modifier.fillMaxWidth(0.9f)
    ) { Text("Добавить приказ") }

    Button(
        onClick = { if (reportCount > 1) reportCount--; },
        modifier = Modifier.fillMaxWidth(0.9f)
    ) { Text("Убрать приказ") }
}

@Composable
fun studentNameField(studentName: String, onStudentNameChange: (String) -> Unit) =
    Row(Modifier.fillMaxWidth(0.9F), Arrangement.spacedBy(10.dp), Alignment.CenterVertically) {

        var expanded by remember { mutableStateOf(false) }

        TextField(
            label = { Text("Ф.И.О Студента") },
            modifier = Modifier.fillMaxWidth().onFocusChanged { expanded = it.hasFocus },
            value = studentName,
            onValueChange = onStudentNameChange,
            singleLine = true,
        )
    }

@Composable
fun renderMainInputs(
    showReport: Boolean,
    onChangeReport: (Boolean) -> Unit,
) = Column(Modifier.fillMaxWidth().padding(top = 30.dp), Arrangement.spacedBy(10.dp), Alignment.CenterHorizontally) {
    tableType()
    renderCheckboxField(showReport, onChangeReport)
}
