package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.circularIterator

class Day06 : Day() {
    override val example = DAY_06_EXAMPLE

    private val map = parse()

    override fun partOne(): AocResult = Solution(predictGuardPath().count())

    override fun partTwo(): AocResult = Solution(simulateObstructions())

    private fun simulateObstructions(): List<Cell> {
        TODO()
    }

    private fun parse() = input.map(String::toList)

    private fun predictGuardPath(): Set<Pair<Int, Int>> {
        val visits = mutableSetOf<Pair<Int, Int>>()

        val directions = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT).circularIterator()

        var guard = Guard(startLocation(map), directions.next())

        while (!guard.isOutOfBounds(map)) {
            val nextPosition = guard.nextPosition()

            if (!nextPosition.isOutOfBounds(map) && map[nextPosition.row][nextPosition.col] == '#') {
                guard = guard.changeDirection(directions.next())
            }

            visits.add(Pair(guard.row, guard.col))

            guard = guard.nextPosition()
        }

        return visits
    }

    private data class Guard(
        val cell: Cell,
        val direction: Direction,
    ) {
        val row: Int = cell.row
        val col: Int = cell.col

        fun changeDirection(newDirection: Direction) = this.copy(direction = newDirection)

        fun nextPosition(): Guard = Guard(Cell(row + direction.rowOffset, col + direction.colOffset), direction)

        fun <T> isOutOfBounds(matrix: List<List<T>>): Boolean = cell.isOutOfBounds(matrix)
    }

    private fun startLocation(map: List<List<Char>>): Cell {
        map.forEachIndexed { rowIndex, row ->
            val colIndex = row.indexOf('^')
            if (colIndex != -1) {
                return Cell(rowIndex, colIndex)
            }
        }
        return Cell(-1, -1)
    }
}

const val DAY_06_EXAMPLE = """
    ....#.....
    .........#
    ..........
    ..#.......
    .......#..
    ..........
    .#..^.....
    ........#.
    #.........
    ......#...
"""

fun main() = Day06().solve()
