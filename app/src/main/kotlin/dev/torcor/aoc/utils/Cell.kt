package dev.torcor.aoc.utils

data class Cell(
    val row: Int,
    val col: Int,
) {
    fun <T> isOutOfBounds(grid: List<List<T>>): Boolean {
        val rows = grid.size
        val cols = grid[0].size

        return row !in 0..<rows || col !in 0..<cols
    }
}
