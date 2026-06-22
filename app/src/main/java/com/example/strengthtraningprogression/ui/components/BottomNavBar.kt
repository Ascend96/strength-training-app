package com.example.strengthtraningprogression.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo("home")
                    launchSingleTop = true
                }
            },
            icon = { Text("🏠") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == "progress",
            onClick = {
                navController.navigate("progress") {
                    launchSingleTop = true
                }
            },
            icon = { Text("📊") },
            label = { Text("Progress") }
        )

        NavigationBarItem(
            selected = currentRoute == "exercises",
            onClick = {
                navController.navigate("exercises") {
                    launchSingleTop = true
                }
            },
            icon = { Text("🏋️") },
            label = { Text("Exercises") }
        )
    }
}
