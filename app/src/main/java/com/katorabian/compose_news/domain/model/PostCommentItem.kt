package com.katorabian.compose_news.domain.model

class PostCommentItem(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationDate: String
)
