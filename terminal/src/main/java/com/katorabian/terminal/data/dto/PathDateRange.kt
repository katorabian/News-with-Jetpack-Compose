package com.katorabian.terminal.data.dto

data class PathDateRange(
    val from: String,
    val to: String
)

infix fun String.to(until: String) = PathDateRange(this, until)
