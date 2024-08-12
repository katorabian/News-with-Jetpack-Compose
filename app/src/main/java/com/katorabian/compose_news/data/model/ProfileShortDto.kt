package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class ProfileShortDto(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("second_name") val secondName: String,
    @SerializedName("photo_100") val avatarUrl: String
)