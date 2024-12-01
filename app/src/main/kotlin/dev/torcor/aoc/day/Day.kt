package dev.torcor.aoc.day

import dev.torcor.aoc.client.AocClient
import kotlinx.coroutines.runBlocking

open class Day {
    open fun partOne(): String = "Unsolved"

    open fun partTwo(): String = "Unsolved"

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

        val emoji = if (number in 1..25) emojis[number - 1] else "❓" // Fallback for invalid day
        println("$emoji Advent of Code - Day $number")
        println("  ❄️ Part 1: ${partOne()}")
        println("  🎅 Part 2: ${partTwo()}")
        println()
    }

    val number: Int
        get() = try {
            this.javaClass.simpleName
                .substring(3..4)
                .toInt()
        } catch (e: Exception) {
            println("Invalid day format: ${this.javaClass.simpleName}")
            throw e
        }

    val input: List<String> = runBlocking { AocClient.input(number) }
}
