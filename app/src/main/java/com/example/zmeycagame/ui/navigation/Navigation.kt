package com.example.zmeycagame.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zmeycagame.ui.game.GameScreen
import com.example.zmeycagame.ui.menu.MainMenuScreen
import com.example.zmeycagame.ui.menu.MainMenuViewModel
import com.example.zmeycagame.ui.screens.AboutScreen
import com.example.zmeycagame.ui.screens.HighScoreScreen

@Composable
fun GameNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            val viewModel: MainMenuViewModel = hiltViewModel()
            MainMenuScreen(
                bestScoreRepository = viewModel.bestScoreRepository,
                onStartGame = { navController.navigate("game") },
                onNavigateToHighScore = { navController.navigate("high_score") },
                onNavigateToAbout = { navController.navigate("about") }
            )
        }
        composable("game") {
            GameScreen(onBack = { navController.popBackStack() })
        }
        composable("high_score") {
            HighScoreScreen(onBack = { navController.popBackStack() })
        }
        composable("about") {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}