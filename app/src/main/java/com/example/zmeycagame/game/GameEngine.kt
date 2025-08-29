package com.example.zmeycagame.game

import kotlin.random.Random

data class Cell(val x: Int, val y: Int)

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun isOpposite(other: Direction): Boolean {
        return when (this) {
            UP -> other == DOWN
            DOWN -> other == UP
            LEFT -> other == RIGHT
            RIGHT -> other == LEFT
        }
    }
}

enum class GameStatus {
    RUNNING,
    PAUSED,
    GAME_OVER
}

data class GameState(
    val snake: List<Cell> = listOf(Cell(5, 5)),
    val direction: Direction = Direction.RIGHT,
    val food: Cell = Cell(10, 10),
    val score: Int = 0,
    val bestScore: Int = 0,
    val status: GameStatus = GameStatus.RUNNING,
    val gridSize: Int = 20
)

sealed class GameAction {
    object StartGame : GameAction()
    object RestartGame : GameAction()
    data class Swipe(val direction: Direction) : GameAction()
    object PauseGame : GameAction()
    object ResumeGame : GameAction()
}

object GameEngine {

    private const val GRID_WIDTH = 20
    private const val GRID_HEIGHT = 20

    fun tick(currentState: GameState): GameState {
        if (currentState.status != GameStatus.RUNNING) {
            return currentState
        }

        val newHead = getNewHead(currentState.snake.first(), currentState.direction)

        if (isWallCollision(newHead) || isSelfCollision(newHead, currentState.snake)) {
            return currentState.copy(status = GameStatus.GAME_OVER)
        }

        val newSnake = currentState.snake.toMutableList().apply { add(0, newHead) }

        val foodEaten = newHead == currentState.food
        if (!foodEaten) {
            newSnake.removeLast()
        }

        val newFood = if (foodEaten) {
            generateNewFood(newSnake)
        } else {
            currentState.food
        }

        val newScore = if (foodEaten) currentState.score + 1 else currentState.score

        return currentState.copy(
            snake = newSnake,
            food = newFood,
            score = newScore
        )
    }

    private fun getNewHead(currentHead: Cell, direction: Direction): Cell {
        return when (direction) {
            Direction.UP -> currentHead.copy(y = currentHead.y - 1)
            Direction.DOWN -> currentHead.copy(y = currentHead.y + 1)
            Direction.LEFT -> currentHead.copy(x = currentHead.x - 1)
            Direction.RIGHT -> currentHead.copy(x = currentHead.x + 1)
        }
    }

    private fun isWallCollision(head: Cell): Boolean {
        return head.x < 0 || head.x >= GRID_WIDTH || head.y < 0 || head.y >= GRID_HEIGHT
    }

    private fun isSelfCollision(head: Cell, snake: List<Cell>): Boolean {
        return snake.contains(head)
    }

    private fun generateNewFood(snake: List<Cell>): Cell {
        var newFood: Cell
        do {
            newFood = Cell(Random.nextInt(GRID_WIDTH), Random.nextInt(GRID_HEIGHT))
        } while (snake.contains(newFood))
        return newFood
    }
}