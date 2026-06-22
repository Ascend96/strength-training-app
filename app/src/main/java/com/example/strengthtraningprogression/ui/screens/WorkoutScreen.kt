package com.example.strengthtraningprogression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.strengthtraningprogression.ui.components.AppTopBar
import com.example.strengthtraningprogression.ui.components.BottomNavBar
import com.example.strengthtraningprogression.ui.components.BottomSpacer
import com.example.strengthtraningprogression.ui.components.WorkoutExerciseCard
import com.example.strengthtraningprogression.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    navController: NavController
) {
    val viewModel: WorkoutViewModel = hiltViewModel()
    val workout by viewModel.workout.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var exerciseName by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        topBar = {
            AppTopBar(
                title = "Workout",
                onBack = { navController.popBackStack() }
            )
        },

        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = "workout"
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            item {
                OutlinedTextField(
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    label = { Text("Exercise Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (exerciseName.isNotBlank()) {
                            viewModel.addExercise(exerciseName)
                            exerciseName = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Exercise")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(workout.exercises) { _, exercise ->
                WorkoutExerciseCard(
                    exercise = exercise,
                    onDeleteExercise = { viewModel.deleteExercise(exercise.id) },
                    onDeleteSet = { setIndex -> viewModel.deleteSet(exercise.id, setIndex) },
                    onAddSet = { reps, weight -> viewModel.addSet(exercise.id, reps, weight) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val hasNoExercises = workout.exercises.isEmpty()
                        val hasEmptyExercises = workout.exercises.any { it.sets.isEmpty() }

                        when {
                            hasNoExercises -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Add at least one exercise before finishing"
                                    )
                                }
                            }

                            hasEmptyExercises -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Each exercise must have at least one set"
                                    )
                                }
                            }

                            else -> {
                                viewModel.finishWorkout()
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Finish Workout")
                }

                BottomSpacer()
            }
        }
    }
}