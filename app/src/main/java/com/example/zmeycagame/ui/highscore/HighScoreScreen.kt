package com.example.zmeycagame.ui.highscore

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.zmeycagame.data.BestScoreRepository
import kotlinx.coroutines.launch

@Composable
fun HighScoreScreen() {
    val context = LocalContext.current
    val bestScoreRepository = BestScoreRepository(context)
    val bestScore by bestScoreRepository.bestScore.collectAsState(initial = 0)
    val coroutineScope = rememberCoroutineScope()
    var showConfirmationDialog by remember { mutableStateOf(false) }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text(text = "Clear Best Score") },
            text = { Text(text = "Are you sure you want to clear the best score?") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            bestScoreRepository.clearBestScore()
                        }
                        showConfirmationDialog = false
                    }
                ) {
                    Text(text = "Clear")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "High Score", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "$bestScore", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { showConfirmationDialog = true }) {
            Text(text = "Clear Best Score")
        }
    }
}
