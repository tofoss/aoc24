package dev.torcor.aoc.client

import io.ktor.client.HttpClient
import io.ktor.client.request.cookie
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

object AocClient {
    private val client = HttpClient()

    private val cache = AocCache.loadCache()

    suspend fun input(day: Int): List<String> {
        val cachedResponse = cache[day.toString()]
        if (cachedResponse != null) {
            return cachedResponse.process()
        }

        val url = "https://adventofcode.com/2024/day/$day/input"

        val res = client.get(url) { cookie("session", System.getenv("SESSION")) }.bodyAsText()

        AocCache.saveToFile(day.toString(), res)

        return res.process()
    }

    private fun String.process() = this.trim().split("\n")
}
