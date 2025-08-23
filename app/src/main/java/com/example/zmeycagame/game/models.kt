package com.example.zmeycagame.game

data class Cell(val x: Int, val y: Int)

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class GameState(
    val snake: List<Cell> = listOf(Cell(5, 5)),
    val direction: Direction = Direction.RIGHT,
    val food: Cell = Cell(10, 10),
    val score: Int = 0,
    val bestScore: Int = 0,
    val isGameOver: Boolean = false,
    val isPaused: Boolean = false
)
