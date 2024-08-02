package com.katorabian.compose_news.domain.model

import androidx.annotation.DrawableRes
import com.katorabian.compose_news.R
import com.katorabian.compose_news.domain.annotation.Temp

@Temp("Until REST provide")
class PostCommentItem(
    val id: Int,
    val authorName: String = "Author",
    @DrawableRes val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long comment text",
    val publicationDate: String = "14:00"
)
