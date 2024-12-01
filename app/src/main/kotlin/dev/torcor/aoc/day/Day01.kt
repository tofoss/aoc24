package dev.torcor.aoc.day

import kotlin.math.abs

class Day01 : Day() {
    override fun partOne(): AocResult = Solution(solvePartOne().toString())

    override fun partTwo(): AocResult = Solution(solvePartTwo().toString())

    private fun parse() = input
        .asSequence()
        .map { it.split("\\s+".toRegex()).map(String::toInt) }
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
