package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class GroupShortDto(
    @SerializedName("id") val id: Long,
    @SerializedName("photo_100") val avatar: String,
    @SerializedName("name") val name: String,
)