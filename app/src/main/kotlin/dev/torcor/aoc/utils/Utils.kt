package dev.torcor.aoc.utils

fun toInts(str: String) = str.split("\\s+".toRegex()).map(String::toInt)

fun numbersFrom(str: String) = "\\d+".toRegex().findAll(str).map { it.value.toInt() }.toList()

fun <T> T.debug(prefix: String? = null, suffix: String? = null): T {
    return this.also { println("${prefix ?: ""}$this${suffix?.let { "$it " } ?: ""}") }
}

fun <T> List<T>.tail() = this.drop(1)

fun <T> List<T>.circularIterator(): Iterator<T> = object : Iterator<T> {
    private var currentIndex = 0

    override fun hasNext(): Boolean = isNotEmpty()

    override fun next(): T {
        if (isEmpty()) throw NoSuchElementException("The list is empty.")
        val value = this@circularIterator[currentIndex]
        currentIndex = (currentIndex + 1) % this@circularIterator.size
        return value
    }
}
