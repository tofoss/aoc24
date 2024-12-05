package dev.torcor.aoc.day

import dev.torcor.aoc.utils.numbersFrom

class Day05 : Day() {
    // override val example = DAY_05_EXAMPLE

    override fun partOne() = solve(::findOrderedUpdates)

    override fun partTwo() = solve(::fixUnorderedUpdates)

    private fun solve(solver: () -> List<Set<Int>>) = Solution(solver().sumOf { it.elementAt(it.size / 2) })

    private fun parse(): Pair<Map<Int, Set<Int>>, List<Set<Int>>> {
        val splitIdx = input.indexOf("")
        val rules = input.subList(0, splitIdx).fold(mutableMapOf<Int, Set<Int>>()) { acc, it -> parseOrdering(it, acc) }
        val updates = input.subList(splitIdx + 1, input.size).map { numbersFrom(it).toSet() }
        return rules to updates
    }

    private fun parseOrdering(rule: String, acc: MutableMap<Int, Set<Int>>): MutableMap<Int, Set<Int>> {
        val (first, last) = rule.split("|").map(String::toInt)
        acc.merge(first, setOf(last)) { existingSet, newSet -> existingSet + newSet }
        return acc
    }

    private fun findOrderedUpdates(): List<Set<Int>> {
        val (rules, updates) = parse()
        return updates.filter { it.isOrderedBy(rules) }
    }

    private fun fixUnorderedUpdates(): List<Set<Int>> {
        val (rules, updates) = parse()

        return updates
            .filter { !it.isOrderedBy(rules) }
            .map { it.sortBy(rules) }
    }

    private fun Set<Int>.sortBy(rules: Map<Int, Set<Int>>): Set<Int> {
        if (this.isOrderedBy(rules)) return this

        val head = first()
        val tail: Set<Int> = drop(1).toSet()

        val difference = tail.minus(rules.getOrDefault(head, emptySet()))

        val newHead = difference + head

        return newHead.sortBy(rules) + tail.minus(newHead).sortBy(rules)
    }

    private fun Set<Int>.isOrderedBy(rules: Map<Int, Set<Int>>): Boolean {
        if (isEmpty()) return true

        val head = first()
        val tail: Set<Int> = drop(1).toSet()
        return rules.getOrDefault(head, emptySet()).containsAll(tail) && tail.isOrderedBy(rules)
    }
}

const val DAY_05_EXAMPLE = """
    47|53
    97|13
    97|61
    97|47
    75|29
    61|13
    75|53
    29|13
    97|29
    53|29
    61|53
    97|53
    61|29
    47|13
    75|47
    97|75
    47|61
    75|61
    47|29
    75|13
    53|13

    75,47,61,53,29
    97,61,53,29,13
    75,29,13
    75,97,47,61,53
    61,13,29
    97,13,75,29,47 
"""

fun main() = Day05().solve()
