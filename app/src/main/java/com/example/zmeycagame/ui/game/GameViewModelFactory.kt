package com.example.zmeycagame.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zmeycagame.data.BestScoreRepository

class GameViewModelFactory(private val bestScoreRepository: BestScoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(bestScoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
