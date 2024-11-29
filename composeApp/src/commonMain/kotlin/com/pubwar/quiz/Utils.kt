package com.pubwar.quiz

fun String.isDigit(): Boolean {
    return this.all { it.isDigit() }
}