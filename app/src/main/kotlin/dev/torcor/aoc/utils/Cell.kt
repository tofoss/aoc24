package dev.torcor.aoc.utils

data class Cell(
    val row: Int,
    val col: Int,
) {
    fun <T> isOutOfBounds(matrix: List<List<T>>): Boolean {
        val rows = matrix.size
        val cols = matrix[0].size

        return row !in 0..<rows || col !in 0..<cols
    }
}
