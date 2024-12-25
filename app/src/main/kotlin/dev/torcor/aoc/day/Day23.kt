package dev.torcor.aoc.day

class Day23 : Day() {
    // override val example = DAY_23_EXAMPLE

    override fun partOne() = Solution {
        val graph = parse()

        val ts = graph.keys.filter { it.startsWith("t") }
        val sets = ts
            .flatMap { t ->
                val neighbors = graph[t]!!
                neighbors
                    .flatMap { n ->
                        val intersect = graph[n]!!.intersect(neighbors)
                        intersect.map { setOf(it, t, n) }
                    }
            }.toSet()

        sets.size
    }

    private fun parse(): Map<String, Set<String>> = input
        .map { it.split("-") }
        .fold(mutableMapOf<String, MutableSet<String>>()) { graph, (n1, n2) ->
            graph.computeIfAbsent(n1) { mutableSetOf() }.add(n2)
            graph.computeIfAbsent(n2) { mutableSetOf() }.add(n1)
            graph
        }
}

const val DAY_23_EXAMPLE = """
kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn
"""

fun main() = Day23().solve()
