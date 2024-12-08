package dev.torcor.aoc.day

import dev.torcor.aoc.utils.toInts

class Day02 : Day() {
    private val decreasing = { a: Int, b: Int -> a - b in 1..3 }
    private val increasing = { a: Int, b: Int -> b - a in 1..3 }

    override fun partOne(): AocResult = Solution { solve(::isStrictlySafe) }

    override fun partTwo(): AocResult = Solution { solve(::isSafeWithDampeners) }

    private fun solve(safetyCheck: (List<Int>) -> Boolean) = parse().filter(safetyCheck).count()

    private fun parse() = input
        .asSequence()
        .map { toInts(it) }

    private fun isStrictlySafe(levels: List<Int>) = isConsistent(levels, decreasing) || isConsistent(levels, increasing)

    private fun isConsistent(ints: List<Int>, comparator: (Int, Int) -> Boolean): Boolean = ints
        .zipWithNext()
        .all { (a, b) -> comparator(a, b) }

    private fun isSafeWithDampeners(ints: List<Int>): Boolean = isStrictlySafe(ints) ||
        checkAfterDampeners(ints, increasing) ||
        checkAfterDampeners(ints, decreasing)

    private fun checkAfterDampeners(levels: List<Int>, comparator: (Int, Int) -> Boolean): Boolean {
        val unsafeIdx = findFirstUnsafeIndex(levels, comparator)
            ?: throw IllegalStateException("Should have an unsafe level: $levels")

        return (0..1).any { isSafeAfterRemoval(levels, unsafeIdx + it, comparator) }
    }

    private fun findFirstUnsafeIndex(levels: List<Int>, comparator: (Int, Int) -> Boolean) = levels
        .zipWithNext()
        .indexOfFirst { (a, b) -> !comparator(a, b) }
        .takeIf { it >= 0 }

    private fun isSafeAfterRemoval(
        levels: List<Int>,
        unsafeIdx: Int,
        comparator: (Int, Int) -> Boolean,
    ) = levels
        .filterIndexed { i, _ -> i != unsafeIdx }
        .let { isConsistent(it, comparator) }
}

const val DAY_02_EXAMPLE = """
    7 6 4 2 1
    1 2 7 8 9
    9 7 6 2 1
    1 3 2 4 5
    8 6 4 4 1
    1 3 6 7 9
"""

fun main() = Day02().solve()