package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.circularIterator
import dev.torcor.aoc.utils.debug

class Day06 : Day() {
    // override val example = DAY_06_EXAMPLE

    override fun partOne(): AocResult = Solution { traceGuardVisits().distinct().count() }

    override fun partTwo(): AocResult = Solution { simulateObstructions().count() }

    private fun parse() = input.map(String::toList)

    private val map: List<List<Char>> = parse()

    private fun simulateObstructions(): List<Result<Cell>> {
        val visits = traceGuardVisits().drop(1)
        return visits.map { simulate(it) }.filter { it.isSuccess }
    }

    private fun simulate(obstruction: Cell): Result<Cell> {
        val map = obstruct(obstruction)

        val visits = mutableSetOf<Guard>()
        val directions = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT).circularIterator()

        var guard = Guard(startLocation(map), directions.next())

        while (!guard.isOutOfBounds(map)) {
            if (visits.contains(guard)) {
                return Result.success(obstruction)
            }

            visits.add(guard)

            guard = guard.step(map, directions)
        }

        return Result.failure(IllegalStateException("The guard escaped"))
    }

    private fun simulateTortoiseAndHare(obstruction: Cell): Result<Cell> {
        val map = obstruct(obstruction)

        val directionsSlow = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT).circularIterator()
        val directionsFast = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT).circularIterator()

        val guard = Guard(startLocation(map), directionsSlow.next()).also { directionsFast.next() }

        var slow = guard
        var fast = guard.step(map, directionsFast).step(map, directionsFast)

        while (!slow.isOutOfBounds(map) && !fast.isOutOfBounds(map)) {
            if (slow.cell == fast.cell && slow.direction == fast.direction) {
                return Result.success(obstruction)
            }
            slow = slow.step(map, directionsSlow)
            fast = fast.step(map, directionsFast).step(map, directionsFast)
        }

        return Result.failure(IllegalStateException("The guard escaped"))
    }

    private fun display(
        map: List<List<Char>>,
        visits: MutableSet<Guard>,
        obstruction: Cell,
    ) {
        val path = map.map { it.toMutableList() }
        path[obstruction.row][obstruction.col] = '0'

        visits.forEach {
            val guardIcon = when (it.direction) {
                Direction.UP -> '^'
                Direction.RIGHT -> '>'
                Direction.DOWN -> 'v'
                Direction.LEFT -> '<'
                else -> 'X'
            }

            if (path[it.row][it.col] != '.') {
                path[it.row][it.col] = '+'
            } else {
                path[it.row][it.col] = guardIcon
            }
        }
        path.mapIndexed { index, chars -> chars.debug("$index -> ", "  |  ${map[index]}") }
        println()
    }

    private fun obstruct(cell: Cell): List<List<Char>> = map.mapIndexed { i, it ->
        if (i == cell.row) {
            it.mapIndexed { index, c -> if (index == cell.col) '#' else c }
        } else {
            it
        }
    }

    private fun traceGuardVisits(): List<Cell> {
        val visits = mutableListOf<Cell>()

        val directions = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT).circularIterator()

        var guard = Guard(startLocation(map), directions.next())

        while (!guard.isOutOfBounds(map)) {
            val nextPosition = guard.nextPosition()

            if (!nextPosition.isOutOfBounds(map) && map[nextPosition.row][nextPosition.col] == '#') {
                guard = guard.changeDirection(directions.next())
            }

            visits.add(guard.cell)

            guard = guard.nextPosition()
        }

        return visits.distinct()
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

        fun step(map: List<List<Char>>, directions: Iterator<Direction>): Guard {
            val nextPosition = nextPosition()

            if (!nextPosition.isOutOfBounds(map) && map[nextPosition.row][nextPosition.col] == '#') {
                return changeDirection(directions.next()).step(map, directions)
            }

            return nextPosition
        }
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
