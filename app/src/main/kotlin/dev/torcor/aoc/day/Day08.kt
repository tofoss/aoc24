package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell

class Day08 : Day() {
    // override val example: String = DAY_08_EXAMPLE

    override fun partOne(): AocResult = Solution {
        solve(::antinodes)
    }

    override fun partTwo(): AocResult = Solution {
        solve(::antinodes2)
    }

    private val grid = input.map { it.toList() }

    private val nodes =
        grid.flatMapIndexed { row, it ->
            it.mapIndexedNotNull { col, freq ->
                if (freq != '.') Node(Cell(row, col), freq) else null
            }
        }

    private val lines = nodes.flatMapIndexed { i, f1 ->
        nodes.drop(i + 1).mapNotNull { f2 -> if (f2 != f1 && f2.frequency == f1.frequency) Pair(f1, f2) else null }
    }

    private fun solve(func: (Pair<Node, Node>) -> List<Node>) = lines
        .flatMap(func)
        .filterNot { it.cell.isOutOfBounds(grid) }
        .distinctBy { it.cell }
        .count()

    private fun antinodes(line: Pair<Node, Node>): List<Node> {
        val (a, b) = line

        return listOf(antinode(a, b), antinode(b, a))
    }

    fun antinode(a: Node, b: Node): Node {
        val rowMod = a.cell.row - b.cell.row
        val colMod = a.cell.col - b.cell.col

        return Node(Cell(a.cell.row + rowMod, a.cell.col + colMod), '#')
    }

    private fun antinodes2(line: Pair<Node, Node>): List<Node> {
        val (a, b) = line

        return line.toList() + antinodesUpdatedModel(a, b) + antinodesUpdatedModel(b, a)
    }

    fun antinodesUpdatedModel(a: Node, b: Node): Sequence<Node> {
        val rowMod = a.cell.row - b.cell.row
        val colMod = a.cell.col - b.cell.col

        return generateSequence(a) { node ->
            val cell = Cell(node.cell.row + rowMod, node.cell.col + colMod)

            if (cell.isOutOfBounds(grid)) null else Node(cell, '#')
        }
    }

    data class Node(
        val cell: Cell,
        val frequency: Char,
    )
}

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
