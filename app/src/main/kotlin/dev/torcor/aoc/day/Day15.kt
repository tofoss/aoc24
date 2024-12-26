package dev.torcor.aoc.day

import dev.torcor.aoc.utils.Cell
import dev.torcor.aoc.utils.allCells
import dev.torcor.aoc.utils.debug
import dev.torcor.aoc.utils.firstCell

class Day15 : Day() {
    //override val example = DAY_15_EXAMPLE

    override fun partOne() = Solution {
        val (world, moves) = parse()
        val robot = world.firstCell('@')!!
        moves.fold(robot) { bot, move ->
            simulate(bot, move, world)
        }
        world.forEach { it.joinToString("").debug() }
        world.allCells('O').sumOf { it.row * 100 + it.col }
    }

    private fun simulate(
        botpos: Cell,
        direction: Direction,
        world: List<MutableList<Char>>,
    ): Cell {
        val nextpos = botpos.move(direction)
        if (world[nextpos] == '#') return botpos
        if (world[nextpos] == '.') {
            world[botpos] = '.'
            world[nextpos] = '@'
            return nextpos
        }
        var boxpos = nextpos
        while (true) {
            if (world[boxpos] == '#') return botpos
            if (world[boxpos] == '.') {
                world[boxpos] = 'O'
                world[nextpos] = '@'
                world[botpos] = '.'
                return nextpos
            }
            boxpos = boxpos.move(direction)
        }
    }

    fun parse(): Pair<List<MutableList<Char>>, List<Direction>> {
        val (world, moves) = inputRaw.trimIndent().split("\n\n")
        return Pair(
            world.split("\n").map { it.toMutableList() },
            moves.split("\n").flatMap {
                it.toList().map {
                    when (it) {
                        '^' -> Direction.UP
                        '>' -> Direction.RIGHT
                        'v' -> Direction.DOWN
                        '<' -> Direction.LEFT
                        else -> throw NotImplementedError(it.toString())
                    }
                }
            },
        )
    }
}

const val DAY_15_EXAMPLE = """
##########
#..O..O.O#
#......O.#
#.OO..O.O#
#..O@..O.#
#O#..O...#
#O..O..O.#
#.OO.O.OO#
#....O...#
##########

<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^

"""

fun main() = Day15().solve()

operator fun <T> List<MutableList<T>>.get(cell: Cell): T = this[cell.row][cell.col]

operator fun <T> List<MutableList<T>>.set(cell: Cell, value: T) {
    this[cell.row][cell.col] = value
}
