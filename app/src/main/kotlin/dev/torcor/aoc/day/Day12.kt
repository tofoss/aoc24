package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.debug

class Day12 : Day() {
    //override val example: String = DAY_12_EXAMPLE

    override fun partOne() = Solution {
        buildRegions().sumOf { it.plots.size * it.plots.sumOf { p -> p.edges } }
    }

    fun parse() = input.mapIndexed { rowIndex, row -> row.mapIndexed { index, c -> Plot(c, Cell(rowIndex, index), 0) } }

    fun buildRegions(): List<Region> {
        val map = parse()
        val visited = mutableSetOf<Plot>()
        val result = mutableListOf<Region>()

        fun dfs(plot: Plot, currentRegion: MutableSet<Plot>) {
            if (plot in visited) return
            visited.add(plot)

            val neighbors = Direction.ULDR.map {
                plot.neighbor(it, map)
            }
            val edges = neighbors.count { it == null }

            neighbors.filterNotNull().forEach { dfs(it, currentRegion) }

            currentRegion.add(plot.copy(edges = edges))
        }

        map.forEach { row ->
            row.forEach { col ->
                val currentRegion = mutableSetOf<Plot>()
                dfs(col, currentRegion)
                if (currentRegion.isNotEmpty()) {
                    result.add(Region(currentRegion.first().plant, currentRegion.toSet()))
                }
            }
        }

        return result
    }

    data class Plot(
        val plant: Char,
        val cell: Cell,
        val edges: Int,
    ) {
        fun neighbor(direction: Direction, map: List<List<Plot>>): Plot? {
            val nextCell = cell.move(direction)
            if (nextCell.isOutOfBounds(map)) return null

            val neighbor = map[nextCell.row][nextCell.col]

            return if (neighbor.plant == plant) {
                neighbor
            } else {
                null
            }
        }
    }

    data class Region(
        val plant: Char,
        val plots: Set<Plot>,
    )
}

const val DAY_12_EXAMPLE = """
    RRRRIICCFF
    RRRRIICCCF
    VVRRRCCFFF
    VVRCCCJFFF
    VVVVCJJCFE
    VVIVCCJJEE
    VVIIICJJEE
    MIIIIIJJEE
    MIIISIJEEE
    MMMISSJEEE
"""

fun main() = Day12().solve()
