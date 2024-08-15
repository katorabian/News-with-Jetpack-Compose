package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.NewsFeedRepository

class GetRecommendationUseCase(private val repository: NewsFeedRepository) {
    fun get() = repository.getRecommendations()
}