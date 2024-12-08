package dev.torcor.aoc.day

sealed interface AocResult

data class Solution<T>(
    private val answer: () -> T,
) : AocResult {
    val result: T
    val elapsedMs: Long

    init {
        val start = System.currentTimeMillis()
        result = answer()
        elapsedMs = System.currentTimeMillis() - start
    }

    override fun toString(): String {
        return "$result \uD83C\uDF89 \n\t ⏱\uFE0F Time $elapsedMs ms"
    }
}

data object Unsolved : AocResult {
    override fun toString(): String = "\uD83C\uDF81"
}
