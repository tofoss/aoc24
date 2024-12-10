package dev.torcor.aoc.utils

import dev.torcor.aoc.day.Direction

class Graph<T> {
    private val nodes = mutableMapOf<T, Node<T>>()

    fun addNode(value: T) {
        nodes.putIfAbsent(value, Node(value))
    }

    fun addEdge(from: T, to: T) {
        val fromNode = nodes[from] ?: throw IllegalArgumentException("Node $from not found")
        val toNode = nodes[to] ?: throw IllegalArgumentException("Node $to not found")
        fromNode.neighbors.add(toNode)
    }

    fun getNodes(): Collection<Node<T>> = nodes.values

    data class Node<T>(
        val value: T,
        val neighbors: MutableSet<Node<T>> = mutableSetOf(),
    )

    companion object {
        fun <T> fromGrid(
            grid: List<List<T>>,
            directions: List<Direction> = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT),
            hasEdge: (T, T) -> Boolean = { _, _ -> true },
        ): Graph<T> {
            val graph = Graph<T>()
            grid.forEach { row -> row.forEach { col -> graph.addNode(col) } }

            grid.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, value ->
                    val cell = Cell(rowIndex, colIndex)

                    directions.forEach { direction ->
                        val neighboringCell = cell.move(direction)

                        if (!neighboringCell.isOutOfBounds(grid)) {
                            val neighbor = grid[neighboringCell.row][neighboringCell.col]

                            if (hasEdge(value, neighbor)) {
                                graph.addEdge(value, neighbor)
                            }
                        }
                    }
                }
            }

            return graph
        }
    }
}

fun <T> findPathsOfLength(start: Graph.Node<T>, n: Int): List<List<T>> {
    val results = mutableListOf<List<T>>()
    val currentPath = mutableListOf<T>()

    fun dfs(node: Graph.Node<T>) {
        currentPath.add(node.value)

        if (currentPath.size == n) {
            results.add(currentPath)
        } else {
            for (neighbor in node.neighbors) {
                if (neighbor.value !in currentPath) {
                    dfs(neighbor)
                }
            }
        }

        currentPath.removeAt(currentPath.lastIndex)
    }

    dfs(start)

    return results
}
