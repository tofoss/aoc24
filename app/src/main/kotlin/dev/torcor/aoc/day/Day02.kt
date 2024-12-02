package dev.torcor.aoc.day

import dev.torcor.aoc.utils.toInts

class Day02 : Day() {
    // override val example = DAY_02_EXAMPLE

    override fun partOne(): AocResult = Solution(solvePartOne().toString())

    override fun partTwo(): AocResult = super.partTwo()

    private fun parse() = input
        .asSequence()
        .map { toInts(it) }

    private fun solvePartOne() = parse()
        .filter { isSafe(it) }
        .count()

    private fun isSafe(ints: List<Int>): Boolean {
        val decreasing = { a: Int, b: Int -> a - b }
        val increasing = { a: Int, b: Int -> b - a }

        return isConsistently(decreasing, ints) || isConsistently(increasing, ints)
    }

    private fun isConsistently(difference: (Int, Int) -> Int, ints: List<Int>): Boolean = ints
        .zipWithNext { a, b -> difference(a, b) in 1..3 }
        .all { it }
}

const val DAY_02_EXAMPLE = """
    7 6 4 2 1
    1 2 7 8 9
    9 7 6 2 1
    1 3 2 4 5
    8 6 4 4 1
    1 3 6 7 9
"""
