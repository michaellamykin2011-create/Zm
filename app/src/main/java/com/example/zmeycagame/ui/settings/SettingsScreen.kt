package com.example.zmeycagame.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zmeycagame.data.SettingsRepository

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val settingsRepository = SettingsRepository(context)
    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(settingsRepository)
    )

    val gameSpeed by viewModel.gameSpeed.collectAsState()
    val gridSize by viewModel.gridSize.collectAsState()
    val vibrationOn by viewModel.vibrationOn.collectAsState()
    val soundsOn by viewModel.soundsOn.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Game Speed
        Text(text = "Game Speed: $gameSpeed ms")
        Slider(
            value = gameSpeed.toFloat(),
            onValueChange = { viewModel.setGameSpeed(it.toInt()) },
            valueRange = 80f..160f,
            steps = 4
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Grid Size
        Text(text = "Grid Size: $gridSize x $gridSize")
        Slider(
            value = gridSize.toFloat(),
            onValueChange = { viewModel.setGridSize(it.toInt()) },
            valueRange = 16f..24f,
            steps = 1
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Vibration
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Vibration")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = vibrationOn,
                onCheckedChange = { viewModel.setVibration(it) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Sounds
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Sounds")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = soundsOn,
                onCheckedChange = { viewModel.setSounds(it) }
            )
        }
    }
}
