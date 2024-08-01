package com.katorabian.compose_news.presentation.model

import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem

sealed class HomeScreenState {

    data object Initial: HomeScreenState()

    data class Posts(val posts: List<FeedPostItem>): HomeScreenState()

    data class Comments(
        val feedPost: FeedPostItem,
        val comments: List<PostCommentItem>
    ): HomeScreenState()
}