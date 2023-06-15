package app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import generator.generateTable

@Composable
fun button(label: String,  onClick: () -> Unit) =
    Row(Modifier.fillMaxWidth(0.9F), Arrangement.spacedBy(10.dp), Alignment.CenterVertically) {
        Button(
            modifier = Modifier.fillMaxWidth(1.0F),
            onClick = { onClick() }
        ) {
            Text(label)
        }
    }
