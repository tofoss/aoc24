package dev.torcor.aoc.day

import dev.torcor.aoc.utils.numbersFrom
import java.util.LinkedList

class Day03 : Day() {
    // override val example = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    private val mulPattern = "mul\\(\\d{1,3},\\d{1,3}\\)"
    private val doPattern = "do\\(\\)"
    private val dontPattern = "don't\\(\\)"

    override fun partOne() = Solution(parse(mulPattern).sumOf { mul(it) })

    override fun partTwo() = Solution(
        parse("$mulPattern|$doPattern|$dontPattern")
            .toCollection(LinkedList())
            .let { compute(it) },
    )

    private fun parse(pattern: String) = pattern
        .toRegex()
        .findAll(input.joinToString(""))
        .map { it.value }

    private tailrec fun compute(
        operations: LinkedList<String>,
        execute: Boolean = true,
        result: Int = 0,
    ): Int {
        if (operations.isEmpty()) {
            return result
        }
        val op = operations.poll()

        return when {
            op.matches(mulPattern.toRegex()) && execute -> compute(operations, true, result + mul(op))
            op.matches(doPattern.toRegex()) -> compute(operations, true, result)
            else -> compute(operations, false, result)
        }
    }

    private fun mul(str: String) = numbersFrom(str).reduce { acc, i -> acc * i }
}

fun main() = Day03().solve()
