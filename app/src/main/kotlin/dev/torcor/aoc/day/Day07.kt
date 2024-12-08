package dev.torcor.aoc.day

import dev.torcor.aoc.utils.longsFrom

class Day07 : Day() {
    // override val example = DAY_07_EXAMPLE

    private val plus = { a: Long, b: Long -> a + b }
    private val multiply = { a: Long, b: Long -> a * b }
    private val concat = { a: Long, b: Long -> "$a$b".toLong() }

    override fun partOne(): AocResult = Solution { solve(listOf(plus, multiply)) }

    override fun partTwo(): AocResult = Solution { solve(listOf(plus, multiply, concat)) }

    private fun parse() = input.map {
        val (expected, values) = it.split(":").map { s -> longsFrom(s) }
        expected.first() to values
    }

    private fun solve(operators: List<(Long, Long) -> Long>) = parse().sumOf { (expected, values) ->
        test(expected, values, operators)
    }

    private fun test(
        expected: Long,
        values: List<Long>,
        operators: List<(Long, Long) -> Long>,
    ): Long {
        val operatorCombinations = generateLazyCombinations(values.size - 1, operators)

        operatorCombinations.forEach { combination ->
            val result = values.reduceIndexed { index, acc, value ->
                combination[index - 1](acc, value)
            }

            if (result == expected) return expected
        }

        return 0
    }

    private fun generateCombinations(n: Int, operators: List<(Long, Long) -> Long>): List<List<(Long, Long) -> Long>> =
        (1..n).fold(listOf(emptyList())) { acc, _ ->
            acc.flatMap { combination -> operators.map { combination + it } }
        }

    private fun generateLazyCombinations(n: Int, operators: List<(Long, Long) -> Long>): Sequence<List<(Long, Long) -> Long>> = sequence {
        if (n == 0) {
            yield(emptyList())
        } else {
            generateLazyCombinations(n - 1, operators).forEach { combination ->
                operators.forEach { operator ->
                    yield(combination + operator)
                }
            }
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
