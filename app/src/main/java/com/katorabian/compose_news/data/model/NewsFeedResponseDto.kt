package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class NewsFeedResponseDto(
    @SerializedName("response") val newsFeedContentDto: NewsFeedContentDto
)

data class NewsFeedContentDto(
    @SerializedName("items") val posts: List<PostDto>,
    @SerializedName("groups") val groups: List<GroupDto>
)