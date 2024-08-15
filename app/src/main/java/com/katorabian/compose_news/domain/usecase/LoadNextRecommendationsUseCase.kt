package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextRecommendationsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    suspend fun load() = repository.loadNextData()
}