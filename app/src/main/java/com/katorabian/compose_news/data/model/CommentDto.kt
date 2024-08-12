package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val authorId: Long,
    @SerializedName("post_id") val groupId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String
)