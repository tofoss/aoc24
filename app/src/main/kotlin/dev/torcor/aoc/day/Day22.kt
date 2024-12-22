package dev.torcor.aoc.day

class Day22 : Day() {
    //override val example = DAY_22_EXAMPLE

    override fun partOne() = Solution {
        parse().sumOf { it.secretNumbers().last() }
    }

    override fun partTwo() = Solution {
        val priceSequences = parse().map {
            it
                .secretNumbers()
                .map { n -> n.price() }
                .sequences()
        }

        val sequenceValues = mutableMapOf<List<Long>, Long>()

        priceSequences.forEach { seq ->
            seq.forEach { (k, v) ->
                sequenceValues.merge(k, v) { oldValue, newValue -> oldValue + newValue }
            }
        }

        sequenceValues.maxBy { it.value }
    }

    private val steps = listOf(
        { n: Long -> (n * 64L).mixAndPrune(n) },
        { n: Long -> (n / 32L).mixAndPrune(n) },
        { n: Long -> (n * 2048L).mixAndPrune(n) },
    )

    private fun Long.secretNumbers() = generateSequence(this) { it.secretNumber() }.take(2001)

    private fun Long.secretNumber(): Long = steps.fold(this) { acc, fn -> fn(acc) }

    private fun Long.mixAndPrune(other: Long) = this.xor(other) % 16777216L

    private fun Long.price() = this % 10

    private fun Long.change(previous: Long) = this - previous

    private fun Sequence<Long>.sequences(): MutableMap<List<Long>, Long> {
        val indexed = this.withIndex()
        val prices = this.toList()

        val unique = mutableMapOf<List<Long>, Long>()

        indexed.drop(1).windowed(4).forEach {
            val seq = it.map { (index, n) -> n.change(prices[index - 1]) }
            if (unique[seq] == null) {
                unique[seq] = it.last().value
            }
        }

        return unique
    }

    private fun parse() = input.map { it.toLong() }
}

const val DAY_22_EXAMPLE = """
1
2
3
2024
"""

fun main() = Day22().solve()
