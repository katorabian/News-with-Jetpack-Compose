package com.katorabian.terminal.data.dto

import com.google.gson.annotations.SerializedName

data class BarsResponse(
    @SerializedName("results") val barList: List<BarDto>
)