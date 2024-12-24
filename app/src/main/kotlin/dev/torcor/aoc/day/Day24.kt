package dev.torcor.aoc.day

import dev.torcor.aoc.utils.debug
import java.math.BigInteger

class Day24 : Day() {
    // override val example = DAY_24_EXAMPLE

    override fun partOne() = Solution {
        val (wires, gates) = parse()

        while (gates.isNotEmpty()) {
            val results = gates.mapNotNull { gate ->
                val res = gate.exec(wires)
                res?.let { _ ->
                    wires[res.id] = res
                    gate
                }
            }

            gates.removeAll(results)
        }

        val bits = wires.filterKeys { it.startsWith('z') }.values.sortedByDescending { it.id }

        val binaryString = bits.joinToString("") { it.value!!.toString() }.debug()

        BigInteger(binaryString, 2)
    }

    private fun parse(): Pair<MutableMap<String, Wire>, MutableList<Gate>> {
        val (init, ops) = inputRaw.trimIndent().split("\n\n")

        val wires = init
            .split("\n")
            .associate {
                val (id, value) = it.split(":")
                id.trim() to Wire(id.trim(), value.trim().toInt())
            }.toMutableMap()

        val gates = ops.split("\n").map {
            val ws = "[a-z0-9]+".toRegex().findAll(it).map { it.value }.toList()
            val op = "[A-Z]+".toRegex().find(it)!!.value

            assert(ws.size == 3)

            ws.forEach { w ->
                if (wires[w] == null) {
                    wires[w] = Wire(w)
                }
            }

            Gate(ws[0], ws[1], ws[2], op.fn())
        }

        return Pair(wires, gates.toMutableList())
    }

    private fun String.fn(): (Int, Int) -> Int = when (this) {
        "OR" -> { a: Int, b: Int -> a or b }
        "XOR" -> { a: Int, b: Int -> a xor b }
        "AND" -> { a: Int, b: Int -> a and b }
        else -> throw NotImplementedError(this)
    }

    data class Wire(
        val id: String,
        val value: Int? = null,
    )

    data class Gate(
        val a: String,
        val b: String,
        val output: String,
        val op: (Int, Int) -> Int,
    ) {
        fun exec(wires: Map<String, Wire>): Wire? = if (wires[a]?.value == null || wires[b]?.value == null) {
            null
        } else {
            wires[output]!!.copy(value = op(wires[a]?.value!!, wires[b]?.value!!))
        }
    }
}

const val DAY_24_EXAMPLE = """
x00: 1
x01: 0
x02: 1
x03: 1
x04: 0
y00: 1
y01: 1
y02: 1
y03: 1
y04: 1

ntg XOR fgs -> mjb
y02 OR x01 -> tnw
kwq OR kpj -> z05
x00 OR x03 -> fst
tgd XOR rvg -> z01
vdt OR tnw -> bfw
bfw AND frj -> z10
ffh OR nrd -> bqk
y00 AND y03 -> djm
y03 OR y00 -> psh
bqk OR frj -> z08
tnw OR fst -> frj
gnj AND tgd -> z11
bfw XOR mjb -> z00
x03 OR x00 -> vdt
gnj AND wpb -> z02
x04 AND y00 -> kjc
djm OR pbm -> qhw
nrd AND vdt -> hwm
kjc AND fst -> rvg
y04 OR y02 -> fgs
y01 AND x02 -> pbm
ntg OR kjc -> kwq
psh XOR fgs -> tgd
qhw XOR tgd -> z09
pbm OR djm -> kpj
x03 XOR y03 -> ffh
x00 XOR y04 -> ntg
bfw OR bqk -> z06
nrd XOR fgs -> wpb
frj XOR qhw -> z04
bqk OR frj -> z07
y03 OR x01 -> nrd
hwm AND bqk -> z03
tgd XOR rvg -> z12
tnw OR pbm -> gnj
"""

fun main() = Day24().solve()
