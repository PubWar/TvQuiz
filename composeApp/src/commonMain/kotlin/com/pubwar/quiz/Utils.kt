package com.pubwar.quiz

fun String.isDigit(): Boolean {
    return this.all { it.isDigit() }
}

fun String.myset(index: Int, char: Char): String {
    if (index < 0 || index >= this.length) return this
    val chars = this.padEnd(index + 1, ' ').toCharArray()
    chars[index] = char
    return chars.concatToString()
}