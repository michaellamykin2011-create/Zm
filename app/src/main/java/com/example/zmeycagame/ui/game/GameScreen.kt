package com.example.zmeycagame.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zmeycagame.game.Cell
import com.example.zmeycagame.game.Direction
import com.example.zmeycagame.game.GameAction
import com.example.zmeycagame.game.GameState
import com.example.zmeycagame.game.GameStatus
import kotlin.math.abs

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val gameState by viewModel.gameState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onAction(GameAction.StartGame)
    }

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
                    .border(3.dp, Color.LightGray)
            ) {
                GameBoard(
                    gameState = gameState,
                    onSwipe = { direction -> viewModel.onAction(GameAction.Swipe(direction)) }
                )
            }
            GameControls(
                onAction = viewModel::onAction,
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
                    val (width, height) = size
                    val (x, y) = offset

                    val verticalSwipe = abs(y - height / 2) > abs(x - width / 2)

                    if (verticalSwipe) {
                        if (y < height / 2) onSwipe(Direction.UP) else onSwipe(Direction.DOWN)
                    } else {
                        if (x < width / 2) onSwipe(Direction.LEFT) else onSwipe(Direction.RIGHT)
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
            size = Size(cellSize, cellSize)
        )
    }
}

private fun DrawScope.drawFood(food: Cell, cellSize: Float, color: Color) {
    drawRect(
        color = color,
        topLeft = Offset(x = food.x * cellSize, y = food.y * cellSize),
        size = Size(cellSize, cellSize)
    )
}

private fun DrawScope.drawGameOver(width: Float, height: Float) {
    drawRect(
        color = Color.Black.copy(alpha = 0.5f),
        topLeft = Offset.Zero,
        size = Size(width, height)
    )
}

private fun DrawScope.drawPaused(width: Float, height: Float) {
    drawRect(
        color = Color.Black.copy(alpha = 0.5f),
        topLeft = Offset.Zero,
        size = Size(width, height)
    )
}

@Composable
fun GameControls(
    onAction: (GameAction) -> Unit,
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
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.weight(1f))
            if (gameState.status == GameStatus.RUNNING) {
                IconButton(onClick = { onAction(GameAction.PauseGame) }) {
                    Icon(Icons.Filled.Pause, contentDescription = "Pause")
                }
            } else {
                IconButton(onClick = { onAction(GameAction.ResumeGame) }) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Resume")
                }
            }
            IconButton(onClick = { onAction(GameAction.RestartGame) }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
        Row {
            Button(onClick = { onAction(GameAction.Swipe(Direction.LEFT)) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Left")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Button(onClick = { onAction(GameAction.Swipe(Direction.UP)) }) {
                    Icon(Icons.Filled.ArrowUpward, contentDescription = "Up")
                }
                Button(onClick = { onAction(GameAction.Swipe(Direction.DOWN)) }) {
                    Icon(Icons.Filled.ArrowDownward, contentDescription = "Down")
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onAction(GameAction.Swipe(Direction.RIGHT)) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Right")
            }
        }
    }
}