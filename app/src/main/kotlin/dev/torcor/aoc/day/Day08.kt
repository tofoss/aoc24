package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell

class Day08 : Day() {
    // override val example: String = DAY_08_EXAMPLE

    override fun partOne(): AocResult = Solution {
        findAntinodes().count()
    }

    private val map = input.map { it.toList() }

    private val frequencies =
        map.flatMapIndexed { row, it ->
            it.mapIndexedNotNull { col, freq ->
                if (freq != '.') Frequency(Cell(row, col), freq) else null
            }
        }

    private val lines = frequencies.flatMapIndexed { i, f1 ->
        frequencies.drop(i + 1).mapNotNull { f2 -> if (f2 != f1 && f2.frequency == f1.frequency) Pair(f1, f2) else null }
    }

    private fun findAntinodes() = lines
        .flatMap { antinodes(it) }
        .filterNot { it.cell.isOutOfBounds(map) }
        .distinct()

    private fun antinodes(line: Pair<Frequency, Frequency>): List<Frequency> {
        val (a, b) = line

        return listOf(antinode(a, b), antinode(b, a))
    }

    fun antinode(a: Frequency, b: Frequency): Frequency {
        val rowMod = a.cell.row - b.cell.row
        val colMod = a.cell.col - b.cell.col

        return Frequency(Cell(a.cell.row + rowMod, a.cell.col + colMod), '#')
    }
}

data class Frequency(
    val cell: Cell,
    val frequency: Char,
)

const val DAY_08_EXAMPLE = """
    ............
    ........0...
    .....0......
    .......0....
    ....0.......
    ......A.....
    ............
    ............
    ........A...
    .........A..
    ............
    ............ 
"""

fun main() = Day08().solve()
