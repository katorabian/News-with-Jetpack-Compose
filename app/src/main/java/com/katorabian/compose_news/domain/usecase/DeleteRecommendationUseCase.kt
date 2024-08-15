package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.repository.NewsFeedRepository

class DeleteRecommendationUseCase(private val repository: NewsFeedRepository) {
    suspend fun delete(feedPost: FeedPostItem) = repository.deletePost(feedPost)
}