package dev.torcor

import dev.torcor.aoc.day.Day01
import dev.torcor.aoc.day.Day02
import dev.torcor.aoc.day.Day03
import dev.torcor.aoc.day.Day04
import dev.torcor.aoc.day.Day05
import dev.torcor.aoc.day.Day06
import dev.torcor.aoc.day.Day07
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
    )

    days.forEach { it.solve() }
}
