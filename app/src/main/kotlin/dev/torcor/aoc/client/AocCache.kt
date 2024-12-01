package dev.torcor.aoc.client

import java.io.File

object AocCache {
    private val cacheDir = File("inputs")

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    private fun getFileForDay(day: String): File = File(cacheDir, "day_$day.txt")

    fun loadCache(): Map<String, String> = cacheDir.listFiles()?.associate {
        it.name.removePrefix("day_").removeSuffix(".txt") to it.readText()
    } ?: emptyMap()

    fun saveToFile(day: String, body: String) {
        getFileForDay(day).writeText(body)
    }
}