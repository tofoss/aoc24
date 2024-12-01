package dev.torcor

import dev.torcor.aoc.day.Day01
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val days = listOf(
        Day01(),
    )

    days.forEach { it.solve() }
}
