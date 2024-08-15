package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.AuthRepository

class RefreshAuthStateUseCase(private val repository: AuthRepository) {
    suspend fun refresh() = repository.refreshAuthState()
}