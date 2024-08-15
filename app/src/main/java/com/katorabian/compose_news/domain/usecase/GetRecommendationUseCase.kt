package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetRecommendationUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    fun get() = repository.getRecommendations()
}