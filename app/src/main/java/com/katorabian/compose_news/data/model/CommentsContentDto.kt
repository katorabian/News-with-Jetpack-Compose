package com.katorabian.compose_news.data.model

import com.google.gson.annotations.SerializedName

data class CommentsContentDto(
    @SerializedName("items") val comments: List<CommentDto>,
    @SerializedName("profiles") val profiles: List<ProfileShortDto>,
    @SerializedName("groups") val communities: List<GroupShortDto>,
    @SerializedName("post_author_id") val postAuthorId: Long
)