package dev.torcor.aoc.day

import dev.torcor.aoc.utils.debug
import dev.torcor.aoc.utils.numbersFrom

class Day13 : Day() {
    override val example = DAY_13_EXAMPLE

    override fun partOne() = Solution {
        parse().sumOf { pressButtons(it) }
    }

    private fun pressButtons(it: Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>>): Int {
        val ax = x(it.third, it.first, it.second)
        val ay = y(it.third, it.first, it.second)

        val bx = x(it.third, it.second, it.first)
        val by = y(it.third, it.second, it.first)

        val asect = ax.intersect(ay)
        val bsect = bx.intersect(by)

        if (asect.size != 1 || bsect.size != 1) {
            it.debug("CASE -> ")
            asect.debug("A -> ")
            bsect.debug("B -> ")
        }


        return (asect.map { it * 3 }.firstOrNull() ?: 0) +
            (bsect.map { it * 1 }.firstOrNull() ?: 0)
    }


    fun parse() = inputRaw.trim().split("\n\n").map {
        val numbers = it.split("\n").map { numbersFrom(it) }
        Triple(
            Pair(numbers[0][0], numbers[0][1]),
            Pair(numbers[1][0], numbers[1][1]),
            Pair(numbers[2][0], numbers[2][1]),
        )
    }
}

const val DAY_13_EXAMPLE = """
    Button A: X+94, Y+34
    Button B: X+22, Y+67
    Prize: X=8400, Y=5400

    Button A: X+26, Y+66
    Button B: X+67, Y+21
    Prize: X=12748, Y=12176

    Button A: X+17, Y+86
    Button B: X+84, Y+37
    Prize: X=7870, Y=6450

    Button A: X+69, Y+23
    Button B: X+27, Y+71
    Prize: X=18641, Y=10279 
"""

fun main() {
    /*
     val prize = Pair(7870, 6450)
     val a = Pair(17, 86)
     val b = Pair(84, 37)
     */
    foo(Pair(8400, 5400), Pair(94, 34), Pair(22, 67))
    foo(Pair(7870, 6450), Pair(17, 86), Pair(84, 37))

    println("SHOULD NOT CONTAIN MATCHES:")
    foo(Pair(12784, 12176), Pair(26, 66), Pair(67, 21))
    foo(Pair(18641, 10279), Pair(69, 23), Pair(27, 71))

    Day13().solve()
}

private fun foo(
    prize: Pair<Int, Int>,
    a: Pair<Int, Int>,
    b: Pair<Int, Int>,
) {
    prize.debug()
    x(prize, a, b).debug("ax -> ")
    y(prize, a, b).debug("ay -> ")
    println()
    x(prize, b, a).debug("bx -> ")
    y(prize, b, a).debug("by -> ")
    println()
    println()
}

private fun x(
    prize: Pair<Int, Int>,
    a: Pair<Int, Int>,
    b: Pair<Int, Int>,
) = (1..100)
    .mapNotNull { i ->
        if ((prize.first - a.first * i) % b.first == 0) {
            i
        } else {
            null
        }
    }.toSet()

private fun y(
    prize: Pair<Int, Int>,
    a: Pair<Int, Int>,
    b: Pair<Int, Int>,
) = (1..100)
    .mapNotNull { i ->
        if ((prize.second - a.second * i) % b.second == 0) {
            i
        } else {
            null
        }
    }.toSet()
