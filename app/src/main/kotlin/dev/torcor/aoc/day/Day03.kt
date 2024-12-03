package dev.torcor.aoc.day

import dev.torcor.aoc.utils.numbersFrom

class Day03 : Day() {
    // override val example = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    private val mulPattern = "mul\\(\\d{1,3},\\d{1,3}\\)"
    private val doPattern = "do\\(\\)"
    private val dontPattern = "don't\\(\\)"

    override fun partOne() = Solution(parse(mulPattern).sumOf { mul(it) })

    override fun partTwo() = Solution(
        parse("$mulPattern|$doPattern|$dontPattern")
            .toList()
            .let { compute(it) },
    )

    private fun parse(pattern: String) = pattern
        .toRegex()
        .findAll(input.joinToString(""))
        .map { it.value }

    private fun compute(operations: List<String>, `do`: Boolean = true): Int {
        if (operations.isEmpty()) {
            return 0
        }
        val op = operations.first()

        return if (op.matches(mulPattern.toRegex()) && `do`) {
            mul(op) + compute(operations.drop(1))
        } else {
            compute(operations.drop(1), op.matches(doPattern.toRegex()))
        }
    }

    private fun mul(str: String) = numbersFrom(str).reduce { acc, i -> acc * i }
}

fun main() = Day03().solve()
