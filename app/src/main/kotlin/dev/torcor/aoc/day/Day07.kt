package dev.torcor.aoc.day

import dev.torcor.aoc.utils.longsFrom
import dev.torcor.aoc.utils.numbersFrom

class Day07 : Day() {
    //override val example = DAY_07_EXAMPLE

    override fun partOne(): AocResult = Solution(solvePartOne())

    private fun parse() = input.map {
        val (test, values) = it.split(":").map { s -> longsFrom(s) }

        test.first() to values
    }

    fun solvePartOne() = parse().map { test(it) }.sum()

    private fun test(it: Pair<Long, List<Long>>): Long {
        val expected = it.first
        val values = it.second

        val operatorCombination = generateOperators(values.size - 1)

        operatorCombination.forEach { combination ->
            val result = values.reduceIndexed { index, acc, value ->
                combination[index - 1](acc, value)
            }

            if (result == expected) {
                return expected
            }
        }

        return 0
    }

    private fun generateOperators(n: Int): List<List<(Long, Long) -> Long>> {
        val operators = listOf({ a: Long, b: Long -> a + b }, { a: Long, b: Long -> a * b })

        return (1..n).fold(listOf(emptyList())) { acc, _ ->
            acc.flatMap { combination -> operators.map { combination + it } }
        }
    }
}

const val DAY_07_EXAMPLE = """
    190: 10 19
    3267: 81 40 27
    83: 17 5
    156: 15 6
    7290: 6 8 6 15
    161011: 16 10 13
    192: 17 8 14
    21037: 9 7 18 13
    292: 11 6 16 20
"""

fun main() = Day07().solve()
