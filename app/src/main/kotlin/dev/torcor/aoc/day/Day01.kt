package dev.torcor.aoc.day

import dev.torcor.aoc.utils.toInts
import kotlin.math.abs

class Day01 : Day() {
    override fun partOne(): AocResult = Solution { solvePartOne() }

    override fun partTwo(): AocResult = Solution { solvePartTwo() }

    private fun parse() = input
        .asSequence()
        .map { toInts(it) }
        .map { it.first() to it.last() }
        .unzip()

    private fun solvePartOne(): Int = parse()
        .toList()
        .map { it.sorted() }
        .reduce { acc, list ->
            acc.zip(list) { a, b -> abs(a - b) }
        }.sum()

    private fun solvePartTwo(): Int {
        val (listOne, listTwo) = parse()

        val counts = listTwo.groupingBy { it }.eachCount()
        return listOne.sumOf { it * counts.getOrDefault(it, 0) }
    }
}

const val DAY_01_EXAMPLE = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
        """

fun main() = Day01().solve()
