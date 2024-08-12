package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class ProfileShortDto(
    @SerializedName("id") val id: Long,
    @SerializedName("photo_100") val avatar: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("second_name") val secondName: String
)