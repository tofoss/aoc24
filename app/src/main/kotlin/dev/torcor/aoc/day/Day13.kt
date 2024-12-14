package dev.torcor.aoc.day

import dev.torcor.aoc.utils.longsFrom

class Day13 : Day() {
    // override val example = DAY_13_EXAMPLE

    override fun partOne() = Solution {
        parse().sumOf { cramers(it) }
    }

    override fun partTwo() = Solution {
        parse().sumOf {
            val priceModifier = 10000000000000L
            val bigPrize = it.third.let { p -> p.copy(p.first + priceModifier, p.second + priceModifier) }
            cramers(it.copy(third = bigPrize))
        }
    }

    /**
     * Cramer's Rule
     * Cramer's Rule is a method from linear algebra for solving systems of two linear equations with two unknowns (aa and bb) using determinants.
     * It calculates aa and bb directly by dividing the determinant of the modified system by the determinant of the original coefficient matrix.
     * It leverages the properties of determinants to find the unique solution (if it exists) to the system of equations.
     *
     * Recognizing a problem as a linear algebraic one with two unknowns is key. Look for these signs:
     *     1. Two Equations: You have two equations that need to be satisfied simultaneously.
     *     2. Two Variables: You’re solving for two unknowns (e.g., aa and bb).
     *     3. Linear Relationship: Each equation is linear, meaning variables are multiplied by constants and summed.
     *     4. Single Solution: The system can potentially be solved exactly, provided the determinant isn’t zero.
     *
     * https://www.reddit.com/r/adventofcode/comments/1hd7irq/2024_day_13_an_explanation_of_the_mathematics/
     */
    private fun cramers(equation: Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>): Long {
        val (buttonA, buttonB, prize) = equation

        val a =
            (prize.first * buttonB.second - prize.second * buttonB.first) /
                (buttonA.first * buttonB.second - buttonA.second * buttonB.first)
        val b =
            (buttonA.first * prize.second - buttonA.second * prize.first) /
                (buttonA.first * buttonB.second - buttonA.second * buttonB.first)

        return if (buttonA.first * a + buttonB.first * b == prize.first &&
            buttonA.second * a + buttonB.second * b == prize.second
        ) {
            a * 3L + b
        } else {
            0
        }
    }

    fun parse() = inputRaw.trim().split("\n\n").map {
        val numbers = it.split("\n").map { longsFrom(it) }
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

fun main() = Day13().solve()
