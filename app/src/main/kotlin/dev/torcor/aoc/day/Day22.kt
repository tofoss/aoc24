package dev.torcor.aoc.day

class Day22 : Day() {
    // override val example = DAY_22_EXAMPLE

    override fun partOne() = Solution {
        parse().sumOf { it.secretNumber2000() }
    }

    private val steps = listOf(
        { n: Long -> (n * 64L).mixAndPrune(n) },
        { n: Long -> (n / 32L).mixAndPrune(n) },
        { n: Long -> (n * 2048L).mixAndPrune(n) },
    )

    private fun Long.secretNumber2000(): Long = (1..2000).fold(this) { acc, _ -> acc.secretNumber() }

    private fun Long.secretNumber(): Long = steps.fold(this) { acc, fn -> fn(acc) }

    private fun Long.mixAndPrune(other: Long) = this.xor(other) % 16777216L

    private fun parse() = input.map { it.toLong() }
}

const val DAY_22_EXAMPLE = """
    1
    10
    100
    2024
"""

fun main() = Day22().solve()
