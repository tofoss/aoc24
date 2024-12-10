package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.Graph
import dev.torcor.aoc.utils.findPathsOfLength

class Day10 : Day() {
    // override val example: String get() = DAY_10_EXAMPLE

    override fun partOne() = solve { it.flatten().distinct().count { (_, value) -> value == 9 } }

    override fun partTwo() = solve { it.size }

    private fun solve(count: (List<List<Pair<Cell, Int>>>) -> Int) = Solution {
        Graph
            .fromGrid(parse()) { a, b -> b.second - a.second == 1 }
            .getNodes()
            .filter { it.value.second == 0 }
            .sumOf { count(findPathsOfLength(it, 10)) }
    }

    private fun parse() = input.mapIndexed { i, it -> it.mapIndexed { j, c -> Pair(Cell(i, j), c.digitToInt()) } }
}

const val DAY_10_EXAMPLE = """
    89010123
    78121874
    87430965
    96549874
    45678903
    32019012
    01329801
    10456732
"""

fun main() = Day10().solve()
