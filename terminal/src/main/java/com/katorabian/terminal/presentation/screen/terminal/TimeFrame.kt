package com.katorabian.terminal.presentation.screen.terminal

import androidx.annotation.StringRes
import com.katorabian.terminal.R

enum class TimeFrame(
    @StringRes val labelResId: Int,
    val urlPath: String
) {
    MIN_5(R.string.timeframe_5_minutes, "5/minute"),
    MIN_15(R.string.timeframe_15_minutes, "15/minute"),
    MIN_30(R.string.timeframe_30_minutes, "30/minute"),
    HOUR_1(R.string.timeframe_1_hour, "1/hour")
}