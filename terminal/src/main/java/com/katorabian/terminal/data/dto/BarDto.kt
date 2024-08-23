package com.katorabian.terminal.data.dto

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import java.util.Calendar
import java.util.Date

@Immutable //should move annotation to domain model if it will be created
data class BarDto(
    @SerializedName("o") val open: Float,
    @SerializedName("c") val close: Float,
    @SerializedName("l") val low: Float,
    @SerializedName("h") val high: Float,
    @SerializedName("t") val time: Long
) {
    val calendar: Calendar
        get() = Calendar.getInstance().also {
            it.time = Date(time)
        }
}