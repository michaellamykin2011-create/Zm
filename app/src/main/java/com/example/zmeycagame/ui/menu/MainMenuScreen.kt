package com.example.zmeycagame.ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zmeycagame.data.BestScoreRepository

@Composable
fun MainMenuScreen(
    bestScoreRepository: BestScoreRepository,
    onStartGame: () -> Unit,
    onNavigateToHighScore: () -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val bestScore by bestScoreRepository.bestScore.collectAsState(initial = 0)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Snake", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Best Score: $bestScore", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onStartGame,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(56.dp)
        ) {
            Text(text = "Start Game")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateToHighScore,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(56.dp)
        ) {
            Text(text = "High Score")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateToAbout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(56.dp)
        ) {
            Text(text = "About")
        }
    }
}
