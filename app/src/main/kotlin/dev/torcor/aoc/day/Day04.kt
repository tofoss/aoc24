package dev.torcor.aoc.day

class Day04 : Day() {
    // override val example = DAY_04_EXAMPLE

    override fun partOne() = Solution { findXmas() }

    override fun partTwo() = Solution { findMasMas() }

    private fun parse() = input.map { it.toCharArray().toList() }

    private fun findMasMas(): Int {
        val matrix = parse()

        val directions = listOf(Pair(Direction.UP_RIGHT, Direction.DOWN_LEFT), Pair(Direction.UP_LEFT, Direction.DOWN_RIGHT))

        return matrix.indices
            .map { row ->
                matrix[row].indices.mapNotNull { col ->
                    directions
                        .mapNotNull { dir ->
                            masOrNull(matrix, dir, row, col)
                        }.takeIf { it.size == 2 }
                }
            }.flatten()
            .count()
    }

    private fun masOrNull(
        matrix: List<List<Char>>,
        dir: Pair<Direction, Direction>,
        row: Int,
        col: Int,
    ): String? {
        val upRow = row + dir.first.rowOffset
        val upCol = col + dir.first.colOffset

        val downRow = row + dir.second.rowOffset
        val downCol = col + dir.second.colOffset

        val maybeMas = if (matrix.containsRowCol(upRow, upCol) && matrix.containsRowCol(downRow, downCol)) {
            matrix[upRow][upCol].toString() + matrix[row][col].toString() + matrix[downRow][downCol].toString()
        } else {
            null
        }

        return maybeMas?.takeIf { it == "MAS" || it == "SAM" }
    }

    private fun findXmas(): Int {
        val matrix = parse()

        return List(matrix.size) { row -> fourLetterWords(matrix, row) }
            .flatten()
            .flatten()
            .count { it == "XMAS" }
    }

    private fun fourLetterWords(matrix: List<List<Char>>, row: Int) = matrix[row].indices.map { col ->
        Direction.entries.map { dir ->
            fourLetterWord(row, dir, col, matrix)
        }
    }

    private fun fourLetterWord(
        row: Int,
        dir: Direction,
        col: Int,
        matrix: List<List<Char>>,
    ): String = (0..3)
        .mapNotNull { step ->
            val newRow = row + dir.rowOffset * step
            val newCol = col + dir.colOffset * step

            if (matrix.containsRowCol(newRow, newCol)) {
                matrix[newRow][newCol]
            } else {
                null
            }
        }.joinToString("")

    private fun <T> List<List<T>>.containsRowCol(row: Int, col: Int): Boolean {
        val rows = size
        val cols = this[0].size

        return row in 0..<rows && col in 0..<cols
    }
}

enum class Direction(
    val rowOffset: Int,
    val colOffset: Int,
) {
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    RIGHT(0, 1),
    DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DOWN_LEFT(1, -1),
    LEFT(0, -1),
    UP_LEFT(-1, -1),
    ;

    companion object {
        val ULDR = listOf(UP, LEFT, DOWN, RIGHT)
    }
}

const val DAY_04_EXAMPLE = """
    MMMSXXMASM
    MSAMXMSMSA
    AMXSXMAAMM
    MSAMASMSMX
    XMASAMXAMM
    XXAMMXXAMA
    SMSMSASXSS
    SAXAMASAAA
    MAMMMXMMMM
    MXMXAXMASX        
    """

fun main() = Day04().solve()
