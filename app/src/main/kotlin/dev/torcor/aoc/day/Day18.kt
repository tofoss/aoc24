package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.numbersFrom

class Day18 : Day() {
    // override val example = DAY_18_EXAMPLE
    // private val range = 0..6
    private val range = 0..70

    override fun partOne() = Solution {
        shortestPath(pushdown(1024), Cell(0, 0), Cell(range.max(), range.max()))
    }

    private fun shortestPath(
        grid: List<MutableList<Char>>,
        start: Cell,
        target: Cell,
    ): Int {
        val queue = ArrayDeque<Pair<Cell, Int>>()
        val visited = mutableSetOf<Cell>()
        queue.add(Pair(start, 0))
        visited.add(start)

        while (queue.isNotEmpty()) {
            val (current, distance) = queue.removeFirst()

            if (current == target) return distance

            current.neighbors(grid).forEach {
                if (it !in visited) {
                    queue.add(Pair(it, distance + 1))
                    visited.add(it)
                }
            }
        }
        return -1
    }

    private fun Cell.neighbors(grid: List<MutableList<Char>>): List<Cell> {
        return Direction.ULDR.mapNotNull {
            val neighbor = this.move(it)
            return@mapNotNull if (neighbor.isOutOfBounds(grid) || grid[neighbor.row][neighbor.col] == '#') {
                null
            } else {
                neighbor
            }
        }
    }

    private fun parse() = input.map {
        val ints = numbersFrom(it)
        Cell(ints.last(), ints.first())
    }

    fun pushdown(bytes: Int): List<MutableList<Char>> {
        val grid = range.map {
            range.map { '.' }.toMutableList()
        }

        val bytePositions = parse()

        (0..<bytes).forEach {
            val byte = bytePositions[it]
            grid[byte.row][byte.col] = '#'
        }

        return grid
    }
}

const val DAY_18_EXAMPLE = """
    5,4
    4,2
    4,5
    3,0
    2,1
    6,3
    2,4
    1,5
    0,6
    3,3
    2,6
    5,1
    1,2
    5,5
    2,5
    6,5
    1,4
    0,4
    6,4
    1,1
    6,1
    1,0
    0,5
    1,6
    2,0
"""

fun main() = Day18().solve()
