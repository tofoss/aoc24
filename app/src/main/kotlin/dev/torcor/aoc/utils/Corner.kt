package dev.torcor.aoc.utils

import dev.torcor.aoc.day.Direction

enum class Corner {
    TopRightOuter,
    TopLeftOuter,
    BotRightOuter,
    BotLeftOuter,
    TopRightInner,
    TopLeftInner,
    BotRightInner,
    BotLeftInner,
}

fun <T> topLeftOuter(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = outerCorner(
    cell,
    grid,
    isDifferent,
    Pair(Direction.UP, Direction.LEFT),
    Corner.TopLeftOuter,
)

fun <T> botLeftOuter(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = outerCorner(
    cell,
    grid,
    isDifferent,
    Pair(Direction.DOWN, Direction.LEFT),
    Corner.BotLeftOuter,
)

fun <T> topRightOuter(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = outerCorner(
    cell,
    grid,
    isDifferent,
    Pair(Direction.UP, Direction.RIGHT),
    Corner.TopRightOuter,
)

fun <T> botRightOuter(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = outerCorner(
    cell,
    grid,
    isDifferent,
    Pair(Direction.DOWN, Direction.RIGHT),
    Corner.BotRightOuter,
)

fun <T> outerCorner(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
    directions: Pair<Direction, Direction>,
    corner: Corner,
): Corner? {
    val a = cell.move(directions.first)
    val b = cell.move(directions.second)
    if (cell.isOutOfBounds(grid)) return null

    val aOutOfBounds = a.isOutOfBounds(grid)
    val bOutOfBounds = b.isOutOfBounds(grid)

    if (aOutOfBounds && bOutOfBounds) return corner

    if (!aOutOfBounds &&
        !bOutOfBounds &&
        isDifferent(grid[a.row][a.col]) &&
        isDifferent(grid[b.row][b.col])
    ) {
        return corner
    }

    if ((aOutOfBounds || isDifferent(grid[a.row][a.col])) &&
        (bOutOfBounds || isDifferent(grid[b.row][b.col]))
    ) {
        return corner
    }
    return null
}

fun <T> topLeftInner(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = innerCorner(
    cell,
    grid,
    isDifferent,
    Triple(Direction.DOWN, Direction.RIGHT, Direction.DOWN_RIGHT),
    Corner.TopLeftInner,
)

fun <T> topRightInner(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = innerCorner(
    cell,
    grid,
    isDifferent,
    Triple(Direction.DOWN, Direction.LEFT, Direction.DOWN_LEFT),
    Corner.TopRightInner,
)

fun <T> botLeftInner(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = innerCorner(
    cell,
    grid,
    isDifferent,
    Triple(Direction.UP, Direction.RIGHT, Direction.UP_RIGHT),
    Corner.BotLeftInner,
)

fun <T> botRightInner(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
) = innerCorner(
    cell,
    grid,
    isDifferent,
    Triple(Direction.UP, Direction.LEFT, Direction.UP_LEFT),
    Corner.BotRightInner,
)

fun <T> innerCorner(
    cell: Cell,
    grid: List<List<T>>,
    isDifferent: (T) -> Boolean,
    directions: Triple<Direction, Direction, Direction>,
    corner: Corner,
): Corner? {
    val a = cell.move(directions.first)
    val b = cell.move(directions.second)
    val diagonal = cell.move(directions.third)
    if (cell.isOutOfBounds(grid)) return null

    if (a.isOutOfBounds(grid) || b.isOutOfBounds(grid) || diagonal.isOutOfBounds(grid)) return null
    if (!isDifferent(grid[a.row][a.col]) && !isDifferent(grid[b.row][b.col]) && isDifferent(grid[diagonal.row][diagonal.col])) {
        return corner
    }
    return null
}

fun <T> Cell.corners(grid: List<List<T>>, isDifferent: (T) -> Boolean): List<Corner> = listOfNotNull(
    topLeftOuter(this, grid, isDifferent),
    topRightOuter(this, grid, isDifferent),
    botLeftOuter(this, grid, isDifferent),
    botRightOuter(this, grid, isDifferent),
    topLeftInner(this, grid, isDifferent),
    topRightInner(this, grid, isDifferent),
    botLeftInner(this, grid, isDifferent),
    botRightInner(this, grid, isDifferent),
)

fun main() {
    fun runTestCase(
        description: String,
        cell: Cell,
        grid: List<List<Char>>,
        isDifferent: (Char) -> Boolean,
        expectedCorner: Corner?,
        testFunction: (Cell, List<List<Char>>, (Char) -> Boolean) -> Corner?,
    ) {
        val result = testFunction(cell, grid, isDifferent)
        println("$description: ${if (result == expectedCorner) "PASS" else "FAIL (Expected: $expectedCorner, Got: $result)"}")
    }

    val grid = listOf(
        listOf('z', 'z', 'x'),
        listOf('x', 'x', 'x'),
    )

    val isDifferent: (Char) -> Boolean = { it == 'z' }

    runTestCase(
        description = "Cell (0, 2) should be TopRightOuter",
        cell = Cell(0, 2),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = Corner.TopRightOuter,
        testFunction = { cell, grid, isDiff -> topRightOuter(cell, grid, isDiff) },
    )

    runTestCase(
        description = "Cell (0, 2) should be TopLeftOuter",
        cell = Cell(0, 2),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = Corner.TopLeftOuter,
        testFunction = { cell, grid, isDiff -> topLeftOuter(cell, grid, isDiff) },
    )

    runTestCase(
        description = "Cell (1, 2) should be BotRightInner",
        cell = Cell(1, 2),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = Corner.BotRightInner,
        testFunction = { cell, grid, isDiff -> botRightInner(cell, grid, isDiff) },
    )

    runTestCase(
        description = "Cell (0, 2) should NOT be BotRightInner",
        cell = Cell(0, 2),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = null,
        testFunction = { cell, grid, isDiff -> botRightInner(cell, grid, isDiff) },
    )

    runTestCase(
        description = "Cell (2, 2) should NOT be BotRightOuter",
        cell = Cell(2, 2),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = null,
        testFunction = { cell, grid, isDiff -> botRightOuter(cell, grid, isDiff) },
    )

    runTestCase(
        description = "Cell (1, 2) should be BotRightInner",
        cell = Cell(1, 2),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = Corner.BotRightInner,
        testFunction = { cell, grid, isDiff -> botRightInner(cell, grid, isDiff) },
    )

    runTestCase(
        description = "Cell (1, 2) should be BotRightOuter",
        cell = Cell(1, 2),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = Corner.BotRightOuter,
        testFunction = { cell, grid, isDiff -> botRightOuter(cell, grid, isDiff) },
    )

    runTestCase(
        description = "Cell (1, 3) should NOT be a corner",
        cell = Cell(1, 3),
        grid = grid,
        isDifferent = isDifferent,
        expectedCorner = null,
        testFunction = { cell, grid, isDiff -> botRightOuter(cell, grid, isDiff) },
    )
}
