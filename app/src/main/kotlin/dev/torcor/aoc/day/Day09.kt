package dev.torcor.aoc.day

import java.util.LinkedList

class Day09 : Day() {
    //override val example = "2333133121414131402"

    override fun partOne() = Solution {
        checksum(fragment())
    }

    override fun partTwo() = Solution {
        checksum(moveWholeFiles().flatMap { file -> List(file.files) { file.index } + List(file.freeSpace) { null } })
    }

    private val blocks = input.first().chunked(2).map { Pair(it[0].toString().toInt(), it.getOrNull(1)?.toString()?.toInt() ?: 0) }

    private val files = input
        .first()
        .chunked(2)
        .mapIndexed { index, it -> FileBlock(index, it[0].toString().toInt(), it.getOrNull(1)?.toString()?.toInt() ?: 0) }

    private val disk = blocks.flatMapIndexed { index, block ->
        List(block.first) { index } + List(block.second) { null }
    }

    private fun checksum(disk: List<Int?>): Long = disk.mapIndexed { index, id -> index * (id?.toLong() ?: 0L) }.sum()

    private fun moveWholeFiles(): List<FileBlock> {
        val filelist = files.toCollection(LinkedList())

        files.reversed().forEachIndexed { index, file ->
            val freeFile = filelist.subList(0, filelist.size - index - 1).firstOrNull { it.freeSpace >= file.files }
            if (freeFile != null) {
                val freeIndex = filelist.indexOf(freeFile)
                filelist[freeIndex] = freeFile.copy(freeSpace = 0)
                filelist.add(freeIndex + 1, file.copy(freeSpace = freeFile.freeSpace - file.files))
                val oldIndex = filelist.indexOfLast { it.index == file.index }
                if (oldIndex != -1) {
                    val oldFile = filelist[oldIndex]
                    filelist[oldIndex] = oldFile.copy(files = 0, freeSpace = oldFile.freeSpace + oldFile.files)
                }
            }
        }

        return filelist
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

    data class FileBlock(
        val index: Int,
        val files: Int,
        val freeSpace: Int,
    )
}

fun main() = Day09().solve()
