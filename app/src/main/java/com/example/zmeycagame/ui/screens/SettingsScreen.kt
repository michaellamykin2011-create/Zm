package com.example.zmeycagame.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zmeycagame.ui.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val gameSpeed by viewModel.gameSpeed.collectAsState()
    val gridSize by viewModel.gridSize.collectAsState()
    val vibrationOn by viewModel.vibrationOn.collectAsState()
    val soundsOn by viewModel.soundsOn.collectAsState()

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
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.setGameSpeed(160) }) {
                    Text("160 ms")
                }
                Button(onClick = { viewModel.setGameSpeed(140) }) {
                    Text("140 ms")
                }
                Button(onClick = { viewModel.setGameSpeed(120) }) {
                    Text("120 ms")
                }
                Button(onClick = { viewModel.setGameSpeed(100) }) {
                    Text("100 ms")
                }
                Button(onClick = { viewModel.setGameSpeed(80) }) {
                    Text("80 ms")
                }
            }
            Text("Game Speed: $gameSpeed ms")
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.setGridSize(16) }) {
                    Text("16x16")
                }
                Button(onClick = { viewModel.setGridSize(20) }) {
                    Text("20x20")
                }
                Button(onClick = { viewModel.setGridSize(24) }) {
                    Text("24x24")
                }
            }
            Text("Grid Size: $gridSize x $gridSize")
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.setVibration(!vibrationOn) }) {
                    Text(if (vibrationOn) "Vibration On" else "Vibration Off")
                }
                Button(onClick = { viewModel.setSounds(!soundsOn) }) {
                    Text(if (soundsOn) "Sounds On" else "Sounds Off")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}