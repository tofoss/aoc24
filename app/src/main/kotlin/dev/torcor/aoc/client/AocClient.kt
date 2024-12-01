package dev.torcor.aoc.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.cookie
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import java.io.File

class AocClient {
    val client =
        HttpClient {
            install(Logging)
        }

    private val cache = AocCache.loadCache()

    suspend fun input(day: String): List<String> {
        val cachedResponse = cache[day]
        if (cachedResponse != null) {
            println("Loaded input for day $day from file")
            return cachedResponse.process()
        }

        val url = "https://adventofcode.com/2024/day/$day/input"

        println("Fetching input from: $url")

        val res = client.get(url) { cookie("session", System.getenv("SESSION")) }.bodyAsText()

        AocCache.saveToFile(day, res)

        return res.process()
    }

    private fun String.process() = this.trim().split("\n")
}

object AocCache {
    private val cacheDir = File("inputs")

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    fun getFileForDay(day: String): File = File(cacheDir, "day_$day.txt")

    fun loadCache(): Map<String, String> = cacheDir.listFiles()?.associate {
        it.name.removePrefix("day_").removeSuffix(".txt") to it.readText()
    } ?: emptyMap()

    fun saveToFile(day: String, body: String) {
        getFileForDay(day).writeText(body)
    }
}
