package com.example.strengthtraningprogression.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.strengthtraningprogression.ui.components.AppTopBar
import com.example.strengthtraningprogression.ui.components.BottomNavBar
import com.example.strengthtraningprogression.ui.components.BottomSpacer
import com.example.strengthtraningprogression.ui.components.TimeRangeSelector
import com.example.strengthtraningprogression.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavController,
) {
    val viewModel: WorkoutViewModel = hiltViewModel()
    val summary by viewModel.summary.collectAsState()

    var selectedRange by remember { mutableStateOf("month") }

    LaunchedEffect(Unit) {
        viewModel.loadSummary("month")
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Progress",
                onBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = "progress"
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            TimeRangeSelector(
                selectedRange = selectedRange,
                onRangeSelected = { range ->
                    selectedRange = range
                    viewModel.loadSummary(range)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Summary",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "🏃 Workouts: ${summary.workoutCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "💪 Exercises: ${summary.exerciseCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "📈 Volume (lbs): ${summary.totalVolume}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            BottomSpacer()
        }
    }
}