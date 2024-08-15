package com.katorabian.compose_news.domain.usecase

import com.katorabian.compose_news.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthStateFlowUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    fun get() = repository.getAuthStateFlow()
}