package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class VkBaseResponseDto<T>(
    @SerializedName("response") val generic: T
)