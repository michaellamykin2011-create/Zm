package com.example.zmeycagame.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zmeycagame.ui.game.GameScreen
import com.example.zmeycagame.ui.screens.AboutScreen
import com.example.zmeycagame.ui.screens.HighScoreScreen
import com.example.zmeycagame.ui.screens.MainMenuScreen
import com.example.zmeycagame.ui.screens.SettingsScreen

@Composable
fun GameNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            MainMenuScreen(
                onStartGame = { navController.navigate("game") },
                onSettings = { navController.navigate("settings") },
                onHighScore = { navController.navigate("high_score") },
                onAbout = { navController.navigate("about") }
            )
        }
        composable("game") {
            GameScreen(onBack = { navController.popBackStack() })
        }
        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable("high_score") {
            HighScoreScreen(onBack = { navController.popBackStack() })
        }
        composable("about") {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}