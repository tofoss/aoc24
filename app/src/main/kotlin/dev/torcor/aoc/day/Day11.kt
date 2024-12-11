package dev.torcor.aoc.day

class Day11 : Day() {
    //override val example: String = "125 17"

    override fun partOne() = solve(25)

    override fun partTwo() = Solution {
        val transitions = buildTree()
        var stoneCounts = parse().groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        repeat(75) {
            val nextCounts = mutableMapOf<Long, Long>()
            for ((stone, count) in stoneCounts) {
                transitions[stone]?.forEach { child ->
                    nextCounts[child] = nextCounts.getOrDefault(child, 0L) + count
                }
            }
            stoneCounts = nextCounts
        }

        stoneCounts.values.sum()
    }

    private fun solve(n: Int) = Solution {
        var stoneCounts = parse().groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        repeat(n) {
            stoneCounts = blink(stoneCounts)
        }
        stoneCounts.values.sum()
    }

    private fun parse(): List<Long> = input.first().split(" ").map { it.toLong() }

    private fun blink(stoneCounts: Map<Long, Long>): Map<Long, Long> {
        val nextCounts = mutableMapOf<Long, Long>()

        for ((stone, count) in stoneCounts) {
            when {
                stone == 0L -> {
                    nextCounts[1L] = nextCounts.getOrDefault(1L, 0L) + count
                }

                stone.toString().length % 2 == 0 -> {
                    val str = stone.toString()
                    val part1 = str.substring(0, str.length / 2).toLong()
                    val part2 = str.substring(str.length / 2).toLong()
                    nextCounts[part1] = nextCounts.getOrDefault(part1, 0L) + count
                    nextCounts[part2] = nextCounts.getOrDefault(part2, 0L) + count
                }

                else -> {
                    val newValue = stone * 2024
                    nextCounts[newValue] = nextCounts.getOrDefault(newValue, 0L) + count
                }
            }
        }

        return nextCounts
    }

    private fun buildTree(): Map<Long, List<Long>> {
        val visited = mutableSetOf<Long>()
        val transitions = mutableMapOf<Long, List<Long>>()

        fun computeBlink(value: Long): List<Long> = when {
            value == 0L -> listOf(1L)
            value.toString().length % 2 == 0 -> {
                val str = value.toString()
                listOf(
                    str.substring(0, str.length / 2).toLong(),
                    str.substring(str.length / 2).toLong(),
                )
            }

            else -> listOf(value * 2024)
        }

        fun dfs(value: Long) {
            if (value in visited) return
            visited.add(value)
            val results = computeBlink(value)
            transitions[value] = results
            results.forEach { dfs(it) }
        }

        val initialValues = parse()
        initialValues.forEach { dfs(it) }

        return transitions
    }
}

fun main() = Day11().solve()
