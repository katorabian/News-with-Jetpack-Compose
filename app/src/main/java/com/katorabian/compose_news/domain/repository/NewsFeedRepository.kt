package com.katorabian.compose_news.domain.repository

import com.katorabian.compose_news.domain.model.FeedPostItem
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getRecommendations(): StateFlow<List<FeedPostItem>>
    suspend fun loadNextData()
    suspend fun changeLikeStatus(feedPost: FeedPostItem)
    suspend fun deletePost(feedPost: FeedPostItem)
}