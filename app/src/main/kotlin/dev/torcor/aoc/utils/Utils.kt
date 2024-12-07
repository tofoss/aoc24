package dev.torcor.aoc.utils

fun toInts(str: String) = str.split("\\s+".toRegex()).map(String::toInt)

fun numbersFrom(str: String) = "\\d+".toRegex().findAll(str).map { it.value.toInt() }.toList()

fun <T> T.debug(prefix: String? = null, suffix: String? = null) =
    this.also { println("${prefix?.let { "$it " } ?: ""}$this${suffix?.let { "$it " } ?: ""}") }

fun <T> List<T>.tail() = this.drop(1)
