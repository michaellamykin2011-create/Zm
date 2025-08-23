package com.example.zmeycagame.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zmeycagame.ui.models.Cell
import com.example.zmeycagame.ui.models.Direction
import com.example.zmeycagame.ui.models.GameState
import com.example.zmeycagame.ui.models.GameStatus

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val gameState by viewModel.gameState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF0B1220))
            ) {
                GameBoard(
                    gameState = gameState,
                    onSwipe = viewModel::onSwipe
                )
            }
            GameControls(
                onDirectionChange = viewModel::onSwipe,
                onPause = viewModel::onPause,
                onResume = viewModel::onResume,
                onRestart = viewModel::restartGame,
                onBack = onBack,
                gameState = gameState,
            )
        }
    }
}

@Composable
fun GameBoard(
    gameState: GameState,
    onSwipe: (Direction) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val center = size.width / 2
                    when {
                        offset.x < center - 50 -> onSwipe(Direction.LEFT)
                        offset.x > center + 50 -> onSwipe(Direction.RIGHT)
                        offset.y < size.height / 2 -> onSwipe(Direction.UP)
                        else -> onSwipe(Direction.DOWN)
                    }
                }
            }
    ) {
        val cellSize = size.width / gameState.gridSize
        drawGrid(gameState.gridSize, cellSize, Color.DarkGray)
        drawSnake(gameState.snake, cellSize, Color(0xFF00D1FF))
        drawFood(gameState.food, cellSize, Color(0xFFFF8A00))

        if (gameState.status == GameStatus.GAME_OVER) {
            drawGameOver(size.width, size.height)
        }

        if (gameState.status == GameStatus.PAUSED) {
            drawPaused(size.width, size.height)
        }
    }
}

private fun DrawScope.drawGrid(gridSize: Int, cellSize: Float, color: Color) {
    for (i in 0..gridSize) {
        drawLine(
            color = color,
            start = Offset(x = 0f, y = i * cellSize),
            end = Offset(x = size.width, y = i * cellSize),
            strokeWidth = 0.5f
        )
        drawLine(
            color = color,
            start = Offset(x = i * cellSize, y = 0f),
            end = Offset(x = i * cellSize, y = size.height),
            strokeWidth = 0.5f
        )
    }
}

private fun DrawScope.drawSnake(snake: List<Cell>, cellSize: Float, color: Color) {
    snake.forEach { cell ->
        drawRect(
            color = color,
            topLeft = Offset(x = cell.x * cellSize, y = cell.y * cellSize),
            size = androidx.compose.ui.geometry.Size(cellSize, cellSize)
        )
    }
}

private fun DrawScope.drawFood(food: Cell, cellSize: Float, color: Color) {
    drawRect(
        color = color,
        topLeft = Offset(x = food.x * cellSize, y = food.y * cellSize),
        size = androidx.compose.ui.geometry.Size(cellSize, cellSize)
    )
}

private fun DrawScope.drawGameOver(width: Float, height: Float) {
    drawRect(
        color = Color.Black.copy(alpha = 0.5f),
        topLeft = Offset.Zero,
        size = androidx.compose.ui.geometry.Size(width, height)
    )
}

private fun DrawScope.drawPaused(width: Float, height: Float) {
    drawRect(
        color = Color.Black.copy(alpha = 0.5f),
        topLeft = Offset.Zero,
        size = androidx.compose.ui.geometry.Size(width, height)
    )
}

@Composable
fun GameControls(
    onDirectionChange: (Direction) -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    gameState: GameState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Score: ${gameState.score}",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.weight(1f))
            if (gameState.status == GameStatus.RUNNING) {
                IconButton(onClick = onPause) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause")
                }
            } else {
                IconButton(onClick = onResume) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Resume")
                }
            }
            IconButton(onClick = onRestart) {
                Icon(Icons.Default.Refresh, contentDescription = "Restart")
            }
        }
        Row {
            Button(onClick = { onDirectionChange(Direction.LEFT) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Left")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Button(onClick = { onDirectionChange(Direction.UP) }) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = "Up")
                }
                Button(onClick = { onDirectionChange(Direction.DOWN) }) {
                    Icon(Icons.Default.ArrowDownward, contentDescription = "Down")
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onDirectionChange(Direction.RIGHT) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Right")
            }
        }
    }
}