package com.katorabian.compose_news.presentation.screen.comments

import com.katorabian.compose_news.domain.model.PostCommentItem

sealed class CommentsScreenState {

    data object Initial: CommentsScreenState()
    data object FirstLoad: CommentsScreenState()

    data class CommentsList(
        val comments: List<PostCommentItem> = emptyList(),
        val nextFrom: String? = null,
        val isLoadMore: Boolean = false,
        val isLoadEnded: Boolean = false
    ): CommentsScreenState()
}