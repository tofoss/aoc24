package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.debug
import dev.torcor.aoc.utils.numbersFrom

class Day14 : Day() {
    // override val example = DAY_14_EXAMPLE

    // private val rows = 7
    // private val cols = 11
    private val rows = 103
    private val cols = 101

    override fun partOne() = Solution {
        val grid = List(rows) { MutableList(cols) { 0 } }

        parse().map { calculateFinalPosition(it) }.forEach {
            grid[it.row][it.col]++
        }

        countRobotsInQuadrants(grid)
    }

    override fun partTwo() = Solution {
        var robots = parse()
        var seconds = 0
        var drawnTree = false

        while (!drawnTree) {
            drawnTree = draw(robots)
            seconds++
            robots = robots.map { it.copy(position = it.position.move(it.velocity)) }
        }

        seconds
    }

    private fun draw(robots: List<Robot>): Boolean {
        val grid = List(rows) { MutableList(cols) { '.' } }
        robots.forEach { grid[it.position.row][it.position.col] = '#' }
        val frame = grid.map { it.joinToString("") }
        if (frame.any { it.contains("########") }) {
            frame.forEach { it.debug() }
            return true
        }
        return false
    }

    private fun countRobotsInQuadrants(grid: List<MutableList<Int>>): Int {
        val qrows = rows / 2
        val qcols = cols / 2

        val topLeft = grid.slice(0..<qrows).map { it.slice(0..<qcols) }
        val topRight = grid.slice(0..<qrows).map { it.slice(qcols + 1..<cols) }
        val botLeft = grid.slice(qrows + 1..<rows).map { it.slice(0..<qcols) }
        val botRight = grid.slice(qrows + 1..<rows).map { it.slice(qcols + 1..<cols) }

        return topLeft.sumOf { it.sum() } *
            topRight.sumOf { it.sum() } *
            botLeft.sumOf { it.sum() } *
            botRight.sumOf { it.sum() }
    }

    private fun calculateFinalPosition(robot: Robot): Cell {
        val velocity = robot.velocity
        return (1..100).fold(robot.position) { cell, i ->
            cell.move(velocity)
        }
    }

    private fun Cell.move(velocity: Velocity): Cell = Cell(
        row = (this.row + velocity.rowV).modWrapAround(rows),
        col = (this.col + velocity.colV).modWrapAround(cols),
    )

    private fun Int.modWrapAround(n: Int): Int {
        val result = this % n
        return if (result < 0) result + n else result
    }

    fun parse() = input.map {
        val points = numbersFrom(it)
        val position = Cell(points[1], points[0])
        val velocity = Velocity(points[3], points[2])
        Robot(position, velocity)
    }

    data class Robot(
        val position: Cell,
        val velocity: Velocity,
    )

    data class Velocity(
        val rowV: Int,
        val colV: Int,
    )
}

const val DAY_14_EXAMPLE = """
    p=0,4 v=3,-3
    p=6,3 v=-1,-3
    p=10,3 v=-1,2
    p=2,0 v=2,-1
    p=0,0 v=1,3
    p=3,0 v=-2,-2
    p=7,6 v=-1,-3
    p=3,0 v=-1,-2
    p=9,3 v=2,3
    p=7,3 v=-1,2
    p=2,4 v=2,-3
    p=9,5 v=-3,-3
"""

fun main() = Day14().solve()
