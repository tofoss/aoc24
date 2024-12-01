package dev.torcor.aoc.day

sealed interface AocResult

data class Solution(
    val answer: String,
) : AocResult {
    override fun toString() = "$answer \uD83D\uDD14"
}

data object Unsolved : AocResult {
    override fun toString(): String = "\uD83C\uDF81"
}
