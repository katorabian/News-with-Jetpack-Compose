package com.katorabian.practice.flow.lesson2

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {
    getFlowByBuilder()
        .filter { it.isPrime() }
        .filter { it > 20 }
        .map {
            println("Map")
            "Number: $it"
        }
        .collect { println(it) }
}

fun getFlowByFlowOfBuilder(): Flow<Int> {
    return flowOf<Int>(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61)
}

fun getFlowByBuilder(): Flow<Int> {
    val numbers = listOf(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61)

    return flow {
        numbers.forEach {
            emit(it)
        }

        val a = 43
        emit(a)
        println("Emitted: $a")
        delay(1000)
        val b = a * 10
        emit(b)
        println("Emitted: $b")
    }
}

suspend fun Int.isPrime(): Boolean {
    if (this <= 1) return false
    for (i in 2 .. this / 2) {
        delay(50)
        if (this % i == 0) return false
    }
    return true
}