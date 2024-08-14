package com.katorabian.compose_news.presentation.screen.comments

import com.katorabian.compose_news.domain.model.PostCommentItem

data class CommentsScreenState(
    val comments: List<PostCommentItem> = emptyList(),
    val isLoading: Boolean = false,
    val nextFrom: String? = null,
    val isLoadEnded: Boolean = false
) {
    val isInitial: Boolean get() = comments.isEmpty() && nextFrom == null
}