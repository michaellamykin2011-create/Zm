package com.example.zmeycagame.game

import kotlin.random.Random

object GameEngine {

    private const val GRID_WIDTH = 20
    private const val GRID_HEIGHT = 20

    fun tick(currentState: GameState, onDirectionChanged: (Direction) -> Unit): GameState {
        if (currentState.isPaused || currentState.isGameOver) {
            return currentState
        }

        val newHead = getNewHead(currentState.snake.first(), currentState.direction)

        if (isWallCollision(newHead) || isSelfCollision(newHead, currentState.snake)) {
            return currentState.copy(isGameOver = true)
        }

        val newSnake = mutableListOf(newHead)
        newSnake.addAll(currentState.snake)

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

    fun restart(currentState: GameState): GameState {
        return GameState(
            snake = listOf(Cell(5, 5)),
            direction = Direction.RIGHT,
            food = generateNewFood(listOf(Cell(5, 5))),
            score = 0,
            bestScore = if(currentState.score > currentState.bestScore) currentState.score else currentState.bestScore,
            isGameOver = false,
            isPaused = false
        )
    }
}
