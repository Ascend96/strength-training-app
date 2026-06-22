package com.example.strengthtraningprogression.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.strengthtraningprogression.viewmodel.ExerciseUiModel

@Composable
fun WorkoutExerciseCard(
    exercise: ExerciseUiModel,
    onDeleteExercise: () -> Unit,
    onDeleteSet: (setIndex: Int) -> Unit,
    onAddSet: (reps: Int, weight: Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                TextButton(onClick = onDeleteExercise) {
                    Text("🗑️")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            exercise.sets.forEachIndexed { setIndex, set ->
                EditableSetRow(
                    index = setIndex,
                    reps = set.reps,
                    weight = set.weight,
                    onDelete = { onDeleteSet(setIndex) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            SetInputRow(onAddSet = onAddSet)
        }
    }
}
