package dev.torcor.aoc.day

import dev.torcor.aoc.utils.debug

class Day11 : Day() {
    //override val example: String = "125 17"

    override fun partOne() = Solution {
        var stones = parse()
        repeat(25) {
            stones = blink(stones)
        }
        stones.size
    }

    fun parse() = input.first().split(" ").map { it.toLong() }

    fun blink(stones: List<Long>): List<Long> = stones.flatMap {
        if (it == 0L) {
            listOf(1)
        } else if (it.toString().length % 2 == 0) {
            val str = it.toString()
            listOf(str.substring(0, str.length / 2).toLong(), str.substring(str.length / 2, str.length).toLong())
        } else {
            listOf(it * 2024)
        }
    }
}

fun main() = Day11().solve()
