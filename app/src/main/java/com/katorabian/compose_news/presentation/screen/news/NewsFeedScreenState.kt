package com.katorabian.compose_news.presentation.screen.news

import com.katorabian.compose_news.domain.model.FeedPostItem

sealed class NewsFeedScreenState {

    data object Initial: NewsFeedScreenState()

    data class Posts(val posts: List<FeedPostItem>): NewsFeedScreenState()

}