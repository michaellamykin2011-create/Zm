package com.example.zmeycagame.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zmeycagame.data.BestScoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun HighScoreScreen(
    onBack: () -> Unit,
    viewModel: HighScoreViewModel = hiltViewModel()
) {
    val bestScore by viewModel.bestScore.collectAsState(initial = 0)
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "High Score",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "$bestScore",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                scope.launch {
                    viewModel.clearBestScore()
                }
            }) {
                Text("Clear High Score")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}

@HiltViewModel
class HighScoreViewModel @Inject constructor(
    private val bestScoreRepository: BestScoreRepository
) : ViewModel() {

    val bestScore = bestScoreRepository.bestScore

    suspend fun clearBestScore() {
        bestScoreRepository.updateBestScore(0)
    }
}