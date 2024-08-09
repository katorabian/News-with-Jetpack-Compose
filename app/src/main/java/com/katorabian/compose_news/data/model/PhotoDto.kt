package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val photoUrls: List<PhotoUrlDto>
)

data class PhotoUrlDto(
    @SerializedName("url") val url: String
)