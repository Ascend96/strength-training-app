package com.example.strengthtraningprogression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.strengthtraningprogression.ui.components.AppTopBar
import com.example.strengthtraningprogression.ui.components.EmptyState
import com.example.strengthtraningprogression.ui.components.SetSummaryCard
import com.example.strengthtraningprogression.viewmodel.ExerciseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    navController: NavController
) {
    val viewModel: ExerciseViewModel = hiltViewModel()
    val exercises by viewModel.exercises.collectAsState()
    val sets by viewModel.exerciseSets.collectAsState()

    val exercise = exercises.find { it.id == exerciseId }

    LaunchedEffect(exerciseId) {
        viewModel.loadSetsForExercise(exerciseId)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Exercise Details",
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->

        if (exercise == null) {
            Box(modifier = Modifier.padding(padding)) {
                Text("Exercise not found")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = exercise.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Category: ${exercise.category}")
            Text("Muscle Group: ${exercise.muscleGroup}")
            Text("Equipment: ${exercise.equipment}")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Recent Sets",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (sets.isEmpty()) {
                EmptyState(message = "No sets recorded yet")
            } else {
                sets.takeLast(5).reversed().forEach { set ->
                    SetSummaryCard(reps = set.reps, weight = set.weight)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = exercise.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}