package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.NewsFeedRepository

class LoadNextRecommendationsUseCase(private val repository: NewsFeedRepository) {
    suspend fun load() = repository.loadNextData()
}