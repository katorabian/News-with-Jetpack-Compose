package com.katorabian.terminal.data.dto

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date

@Parcelize
@Immutable //should move annotation to domain model if it will be created
data class BarDto(
    @SerializedName("o") val open: Float,
    @SerializedName("c") val close: Float,
    @SerializedName("l") val low: Float,
    @SerializedName("h") val high: Float,
    @SerializedName("t") val time: Long
): Parcelable {
    val calendar: Calendar
        get() = Calendar.getInstance().also {
            it.time = Date(time)
        }
}