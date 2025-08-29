package com.example.zmeycagame.game.models

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