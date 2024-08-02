package com.katorabian.compose_news.presentation.model

import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem

sealed class CommentsScreenState {

    data object Initial: CommentsScreenState()

    data class Comments(
        val feedPost: FeedPostItem,
        val comments: List<PostCommentItem>
    ): CommentsScreenState()

}