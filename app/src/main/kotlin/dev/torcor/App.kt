package dev.torcor

import dev.torcor.aoc.day.Day01
import dev.torcor.aoc.day.Day02
import dev.torcor.aoc.day.Day03
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val days = listOf(
        Day01(),
        Day02(),
        Day03(),
    )

    days.forEach { it.solve() }
}
