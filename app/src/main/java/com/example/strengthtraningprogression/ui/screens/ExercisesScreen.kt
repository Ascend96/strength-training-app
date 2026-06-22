package com.example.strengthtraningprogression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.strengthtraningprogression.ui.components.AppTopBar
import com.example.strengthtraningprogression.ui.components.BottomNavBar
import com.example.strengthtraningprogression.ui.components.BottomSpacer
import com.example.strengthtraningprogression.ui.components.EmptyState
import com.example.strengthtraningprogression.ui.components.IconInfoCard
import com.example.strengthtraningprogression.viewmodel.ExerciseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(
    navController: NavController,
) {

    val viewModel: ExerciseViewModel = hiltViewModel()
    val exercises by viewModel.exercises.collectAsState()

    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Exercises",
                onBack = { navController.popBackStack() },
                actions = {
                    TextButton(onClick = {
                        viewModel.addExercise("New Exercise")
                    }) {
                        Text("+")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = "exercises"
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
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Search exercises...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (exercises.isEmpty()) {
                item {
                    EmptyState(message = "No exercises yet")
                }
            }

            items(
                exercises.filter {
                    it.name.contains(searchText, ignoreCase = true)
                }
            ) { exercise ->
                IconInfoCard(
                    title = exercise.name,
                    subtitle = exercise.category,
                    onClick = {
                        navController.navigate("exerciseDetail/${exercise.id}")
                    },
                    trailing = {
                        Text("➡️")
                    }
                )
            }

            item {
                BottomSpacer()
            }
        }
    }
}
