package dev.torcor.aoc.day

sealed interface AocResult

data class Solution<T>(
    val answer: T,
) : AocResult {
    override fun toString() = "$answer \uD83C\uDF89"
}

data object Unsolved : AocResult {
    override fun toString(): String = "\uD83C\uDF81"
}
