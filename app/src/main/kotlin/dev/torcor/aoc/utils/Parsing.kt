package dev.torcor.aoc.utils

fun toInts(it: String) = it.split("\\s+".toRegex()).map(String::toInt)
