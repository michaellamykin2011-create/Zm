package com.example.zmeycagame.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmeycagame.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    val gameSpeed = settingsRepository.gameSpeed
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 120)

    val gridSize = settingsRepository.gridSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 20)

    val vibrationOn = settingsRepository.vibrationOn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    val soundsOn = settingsRepository.soundsOn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    fun setGameSpeed(speed: Int) {
        viewModelScope.launch {
            settingsRepository.setGameSpeed(speed)
        }
    }

    fun setGridSize(size: Int) {
        viewModelScope.launch {
            settingsRepository.setGridSize(size)
        }
    }

    fun setVibration(on: Boolean) {
        viewModelScope.launch {
            settingsRepository.setVibration(on)
        }
    }

    fun setSounds(on: Boolean) {
        viewModelScope.launch {
            settingsRepository.setSounds(on)
        }
    }
}
