package com.example.strengthtraningprogression.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditableSetRow(
    index: Int,
    reps: Int,
    weight: Double,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("${index + 1}", modifier = Modifier.width(40.dp))
        Text("$reps", modifier = Modifier.weight(1f))
        Text("$weight", modifier = Modifier.weight(1f))
        TextButton(onClick = onDelete) {
            Text("🗑️")
        }
    }
}
