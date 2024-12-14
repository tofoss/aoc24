package dev.torcor.aoc.day

import dev.torcor.aoc.client.AocClient
import dev.torcor.aoc.client.process
import kotlinx.coroutines.runBlocking

open class Day {
    open fun partOne(): AocResult = Unsolved

    open fun partTwo(): AocResult = Unsolved

    fun solve() {
        val emojis = listOf(
            "🎄",
            "🎅",
            "🦌",
            "⛄",
            "❄️",
            "🎁",
            "🌟",
            "🕯️",
            "🔔",
            "🛷",
            "🧣",
            "🧤",
            "🍪",
            "🥛",
            "🪵",
            "🔥",
            "🎶",
            "🌌",
            "🦉",
            "🌙",
            "✨",
            "🧑‍🎄",
            "🐧",
            "🎂",
            "🎇",
        )

        val emoji = if (number in 1..25) emojis[number - 1] else "❓"
        println("$emoji Advent of Code - Day $number")
        println("  ❄️ Part 1: ${partOne()}")
        println("  🎅 Part 2: ${partTwo()}")
        println()
    }

    private val number: Int
        get() = try {
            this.javaClass.simpleName
                .substring(3..4)
                .toInt()
        } catch (e: Exception) {
            println("Invalid day format: ${this.javaClass.simpleName}")
            throw e
        }

    open val example: String? = null

    val inputRaw: String get() = example ?: runBlocking { AocClient.input(number) }

    val input: List<String> get() = inputRaw.process()
}
