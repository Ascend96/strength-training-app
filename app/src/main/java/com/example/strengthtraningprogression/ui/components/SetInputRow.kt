package com.example.strengthtraningprogression.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// reps/weight entry form with inline validation; clears itself on successful submit
@Composable
fun SetInputRow(
    onAddSet: (reps: Int, weight: Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var repsInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }

    var repsError by remember { mutableStateOf(false) }
    var weightError by remember { mutableStateOf(false) }

    Column(modifier = modifier) {

        Row {
            OutlinedTextField(
                value = repsInput,
                onValueChange = {
                    repsInput = it
                    repsError = it.toIntOrNull() == null && it.isNotEmpty()
                },
                isError = repsError,
                label = { Text("Reps") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = weightInput,
                onValueChange = {
                    weightInput = it
                    weightError = it.toDoubleOrNull() == null && it.isNotEmpty()
                },
                isError = weightError,
                label = { Text("Weight") },
                modifier = Modifier.weight(1f)
            )
        }

        if (repsError) {
            Text(
                text = "Enter a valid number for reps",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (weightError) {
            Text(
                text = "Enter a valid number for weight",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val reps = repsInput.toIntOrNull()
                val weight = weightInput.toDoubleOrNull()

                repsError = reps == null
                weightError = weight == null

                if (reps != null && weight != null) {
                    onAddSet(reps, weight)

                    repsInput = ""
                    weightInput = ""

                    repsError = false
                    weightError = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("+ Add Set")
        }
    }
}
