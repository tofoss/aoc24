package dev.torcor.aoc.day

class Day19 : Day() {
    // override val example = DAY_19_EXAMPLE

    override fun partOne() = Solution {
        val (patterns, designs) = parse()
        val trie = constructTrie(patterns)

        designs.count { designIsPossible(it, trie) }
    }

    private fun parse(): Pair<List<String>, List<String>> {
        val patterns = inputRaw
            .trimIndent()
            .split("\n\n")
            .first()
            .trim()
            .split(",")
            .map { it.trim() }
        val designs = inputRaw
            .trimIndent()
            .split("\n\n")
            .last()
            .split("\n")

        return Pair(patterns, designs)
    }

    private fun constructTrie(strings: List<String>): Node {
        val root = Node(null)

        strings.forEach { str ->
            var currentNode = root

            str.forEach { c ->
                if (!currentNode.children.contains(c)) {
                    currentNode.children[c] = Node(c)
                }
                currentNode = currentNode.children[c]!!
            }
            currentNode.endOfWord = true
        }

        return root
    }

    private fun designIsPossible(
        design: String,
        root: Node,
        start: Int = 0,
        memo: MutableMap<Int, Boolean> = mutableMapOf(),
    ): Boolean {
        if (start == design.length) return true
        if (start in memo) return memo[start]!!

        var current = root
        for (i in start..<design.length) {
            val char = design[i]
            if (!current.children.contains(char)) break
            current = current.children[char]!!

            if (current.endOfWord) {
                if (designIsPossible(design, root, i + 1, memo)) {
                    memo[start] = true
                    return true
                }
            }
        }

        memo[start] = false
        return false
    }

    data class Node(
        val char: Char?,
        val children: MutableMap<Char, Node> = mutableMapOf(),
        var endOfWord: Boolean = false,
    )
}

const val DAY_19_EXAMPLE = """
    r, wr, b, g, bwu, rb, gb, br

    brwrr
    bggr
    gbbr
    rrbgbr
    ubwu
    bwurrg
    brgr
    bbrgwb
"""

fun main() = Day19().solve()
