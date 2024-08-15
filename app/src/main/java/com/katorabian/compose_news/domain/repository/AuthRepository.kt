package com.katorabian.compose_news.domain.repository

import com.katorabian.compose_news.domain.model.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>
    suspend fun refreshAuthState()
}