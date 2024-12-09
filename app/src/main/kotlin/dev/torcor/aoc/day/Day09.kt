package dev.torcor.aoc.day

class Day09 : Day() {
    //override val example = "2333133121414131402"

    override fun partOne() = Solution {
        checksum(fragment())
    }

    private val blocks = input.first().chunked(2).map { Pair(it[0].toString().toInt(), it.getOrNull(1)?.toString()?.toInt() ?: 0) }

    private val disk = blocks.flatMapIndexed { index, block ->
        List(block.first) { index } + List(block.second) { null }
    }

    private fun checksum(disk: List<Int?>): Long {
        return disk.filterNotNull().mapIndexed { index, i -> index * i.toLong() }.sum()
    }

    private fun fragment(): List<Int?> {
        val mutableDisk = disk.toMutableList()

        fun findLastId(leftHandIndex: Int): Int? {
            for (i in mutableDisk.size - 1 downTo leftHandIndex) {
                if (mutableDisk[i] != null) {
                    val id = mutableDisk[i]
                    mutableDisk[i] = null
                    return id
                }
            }
            return null
        }

        mutableDisk.forEachIndexed { index, id ->
            if (id == null) {
                mutableDisk[index] = findLastId(index)
            }
        }

        return mutableDisk
    }
}

fun main() = Day09().solve()
