package com.example.strengthtraningprogression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.strengthtraningprogression.ui.components.AppTopBar
import com.example.strengthtraningprogression.ui.components.BottomNavBar
import com.example.strengthtraningprogression.ui.components.BottomSpacer
import com.example.strengthtraningprogression.ui.components.IconInfoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onStartWorkout: () -> Unit
){
    val recentWorkouts = listOf(
        Triple("Upper Body", "May 21, 2026", "12 Exercises"),
        Triple("Push Day", "May 19, 2026", "10 Exercises"),
        Triple("Leg Day", "May 17, 2026", "9 Exercises")
    )

    Scaffold(
        topBar = {
            AppTopBar(title = "Smart Log")
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = "home"
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
                Text(
                    text = "Welcome back, User!",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Ready to crush your goals?",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onStartWorkout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Start Workout")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Recent Workouts",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(recentWorkouts) { (name, date, exercises) ->
                IconInfoCard(
                    title = name,
                    subtitle = date,
                    trailing = {
                        Text(
                            text = exercises,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                )
            }

            item {
                BottomSpacer()
            }
        }
    }
}
