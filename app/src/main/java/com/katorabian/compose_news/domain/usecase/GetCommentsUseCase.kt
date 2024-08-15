package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.repository.CommentsRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: CommentsRepository
) {
    suspend fun get(feedPost: FeedPostItem) = repository.getComments(feedPost)
}