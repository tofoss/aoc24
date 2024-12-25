package dev.torcor.aoc.day

class Day25 : Day() {
    // override val example = DAY_25_EXAMPLE

    override fun partOne() = Solution {
        val (keys, locks) = parse()

        keys.sumOf { fits(it, locks) }
    }

    private fun fits(key: List<String>, locks: List<List<String>>): Int = locks.count {
        val lengths = it.map { it.length - 1 }.zip(key.map { it.length - 1 })
        lengths.all { it.first + it.second < 6 }
    }

    fun parse(): Pair<List<List<String>>, List<List<String>>> {
        val keys: MutableList<List<String>> = mutableListOf()
        val locks: MutableList<List<String>> = mutableListOf()

        inputRaw.trimIndent().split("\n\n").forEach {
            val lines = it.split("\n")

            val lst = if (lines[0].all { it == '#' }) locks else keys

            val flipped = List(lines[0].length) { colIdx ->
                List(lines.size) { rowIdx ->
                    lines[rowIdx][colIdx]
                }.filter { it == '#' }.joinToString("")
            }
            lst.add(flipped)
        }

        return Pair(keys, locks)
    }
}

const val DAY_25_EXAMPLE = """
#####
.####
.####
.####
.#.#.
.#...
.....

#####
##.##
.#.##
...##
...#.
...#.
.....

.....
#....
#....
#...#
#.#.#
#.###
#####

.....
.....
#.#..
###..
###.#
###.#
#####

.....
.....
.....
#....
#.#..
#.#.#
#####
"""

fun main() = Day25().solve()
