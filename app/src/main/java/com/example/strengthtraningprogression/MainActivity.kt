package com.example.strengthtraningprogression

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import dagger.hilt.android.AndroidEntryPoint
import com.example.strengthtraningprogression.ui.screens.*
import com.example.strengthtraningprogression.ui.theme.StrengthTraningProgressionTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StrengthTraningProgressionTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                navController = navController,
                onStartWorkout = { navController.navigate("workout") }
            )
        }

        composable("workout") {
            WorkoutScreen(
                navController = navController
            )
        }

        composable("progress") {
            ProgressScreen(
                navController = navController
            )
        }

        composable("exercises") {
            ExercisesScreen(
                navController = navController
            )
        }

        composable("exerciseDetail/{exerciseId}") { backStackEntry ->

            val exerciseId = backStackEntry.arguments?.getString("exerciseId")!!

            ExerciseDetailScreen(
                exerciseId = exerciseId,
                navController = navController
            )
        }
    }
}