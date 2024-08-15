package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun refresh() = repository.refreshAuthState()
}