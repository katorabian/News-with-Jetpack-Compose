package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.repository.NewsFeedRepository
import javax.inject.Inject

class ChangeLikeStatusUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    suspend fun revert(feedPost: FeedPostItem) = repository.changeLikeStatus(feedPost)
}