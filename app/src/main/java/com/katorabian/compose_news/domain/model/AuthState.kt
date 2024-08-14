package com.katorabian.compose_news.domain.model

sealed class AuthState {

    data object Initial: AuthState()
    data object Authorized: AuthState()
    data object Unauthorized: AuthState()
}