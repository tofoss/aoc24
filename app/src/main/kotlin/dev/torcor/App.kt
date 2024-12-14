package dev.torcor

import dev.torcor.aoc.day.Day01
import dev.torcor.aoc.day.Day02
import dev.torcor.aoc.day.Day03
import dev.torcor.aoc.day.Day04
import dev.torcor.aoc.day.Day05
import dev.torcor.aoc.day.Day06
import dev.torcor.aoc.day.Day07
import dev.torcor.aoc.day.Day08
import dev.torcor.aoc.day.Day09
import dev.torcor.aoc.day.Day10
import dev.torcor.aoc.day.Day11
import dev.torcor.aoc.day.Day12
import dev.torcor.aoc.day.Day13
import dev.torcor.aoc.day.Day14
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val days = listOf(
        Day01(),
        Day02(),
        Day03(),
        Day04(),
        Day05(),
        Day06(),
        Day07(),
        Day08(),
        Day09(),
        Day10(),
        Day11(),
        Day12(),
        Day13(),
        Day14(),
    )

    days.forEach { it.solve() }

}
