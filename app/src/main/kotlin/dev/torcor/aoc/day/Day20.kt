package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.debug
import dev.torcor.aoc.utils.firstCell

class Day20 : Day() {
    //override val example = DAY_20_EXAMPLE

    override fun partOne() = Solution {
        val grid = parse()
        val costs = tracePath(grid)
        countShortcuts(costs).count { it >= 100 }
    }

    private fun parse() = input.map { it.toList() }

    private fun countShortcuts(grid: List<List<Int?>>): List<Int> {
        val shortcuts = mutableListOf<Int>()
        var current: Cell? = grid.firstCell(1)!!
        val end = grid.flatten().filterNotNull().max()

        while (current != null) {
            val sc = Direction.ULDR
                .map { current!!.move(it).move(it) }
                .mapNotNull {
                    if (it.isOutOfBounds(grid)) {
                        null
                    } else if (it.value(grid) != null) {
                        it.value(grid)!! - 2 - current!!.value(grid)!!
                    } else {
                        null
                    }
                }

            shortcuts.addAll(sc)

            current = Direction.ULDR.firstNotNullOfOrNull { direction ->
                val next = current!!.move(direction)
                if (next.value(grid) != null && next.value(grid)!! > current!!.value(grid)!!) {
                    next
                } else {
                    null
                }
            }
        }
        return shortcuts
    }

    private fun tracePath(grid: List<List<Char>>): List<List<Int?>> {
        val costMap = List(grid.size) {
            MutableList<Int?>(grid[0].size) { null }
        }

        var current: Cell? = grid.firstCell('S')!!
        var cost = 0

        while (current != null) {
            costMap[current.row][current.col] = cost
            cost += 1

            current = Direction.ULDR.firstNotNullOfOrNull { direction ->
                val next = current!!.move(direction)
                if ((grid[next.row][next.col] == '.' || next.value(grid) == 'E') && costMap[next.row][next.col] == null) {
                    next
                } else {
                    null
                }
            }
        }

        return costMap
    }
}

const val DAY_20_EXAMPLE = """
    ###############
    #...#...#.....#
    #.#.#.#.#.###.#
    #S#...#.#.#...#
    #######.#.#.###
    #######.#.#...#
    #######.#.###.#
    ###..E#...#...#
    ###.#######.###
    #...###...#...#
    #.#####.#.###.#
    #.#...#.#.#...#
    #.#.#.#.#.#.###
    #...#...#...###
    ###############
"""

fun main() = Day20().solve()
