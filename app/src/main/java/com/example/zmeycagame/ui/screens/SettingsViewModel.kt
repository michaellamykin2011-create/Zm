package com.example.zmeycagame.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmeycagame.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val gameSpeed = settingsRepository.gameSpeed.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        120
    )

    fun setGameSpeed(speed: Int) {
        viewModelScope.launch {
            settingsRepository.setGameSpeed(speed)
        }
    }

    val gridSize = settingsRepository.gridSize.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        20
    )

    fun setGridSize(size: Int) {
        viewModelScope.launch {
            settingsRepository.setGridSize(size)
        }
    }

    val vibrationEnabled = settingsRepository.vibrationEnabled.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true
    )

    fun setVibrationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setVibrationEnabled(enabled)
        }
    }

    val soundsEnabled = settingsRepository.soundsEnabled.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true
    )

    fun setSoundsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setSoundsEnabled(enabled)
        }
    }
}