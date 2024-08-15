package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.AuthRepository

class GetAuthStateFlowUseCase(private val repository: AuthRepository) {

    fun get() = repository.getAuthStateFlow()
}