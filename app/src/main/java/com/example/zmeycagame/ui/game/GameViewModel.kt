package com.example.zmeycagame.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmeycagame.data.BestScoreRepository
import com.example.zmeycagame.game.GameEngine
import com.example.zmeycagame.game.models.Direction
import com.example.zmeycagame.game.models.GameState
import com.example.zmeycagame.game.models.GameStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val bestScoreRepository: BestScoreRepository,
) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    private var gameJob: Job? = null

    init {
        viewModelScope.launch {
            bestScoreRepository.bestScore.collect { bestScore ->
                _gameState.value = _gameState.value.copy(bestScore = bestScore)
            }
        }
    }

    fun startGame() {
        if (_gameState.value.status == GameStatus.RUNNING) return

        _gameState.value = GameState(status = GameStatus.RUNNING)
        gameJob = viewModelScope.launch {
            while (true) {
                delay(120)
                _gameState.value = GameEngine.tick(_gameState.value)
                if (_gameState.value.status == GameStatus.GAME_OVER) {
                    if (_gameState.value.score > _gameState.value.bestScore) {
                        bestScoreRepository.updateBestScore(_gameState.value.score)
                    }
                    gameJob?.cancel()
                }
            }
        }
    }

    fun restartGame() {
        gameJob?.cancel()
        _gameState.value = GameState()
        startGame()
    }

    fun onSwipe(direction: Direction) {
        if (_gameState.value.status != GameStatus.RUNNING) return

        val currentDirection = _gameState.value.direction
        if (currentDirection.isOpposite(direction)) return

        _gameState.value = _gameState.value.copy(direction = direction)
    }

    fun onPause() {
        if (_gameState.value.status == GameStatus.RUNNING) {
            _gameState.value = _gameState.value.copy(status = GameStatus.PAUSED)
            gameJob?.cancel()
        }
    }

    fun onResume() {
        if (_gameState.value.status == GameStatus.PAUSED) {
            _gameState.value = _gameState.value.copy(status = GameStatus.RUNNING)
            startGame()
        }
    }
}