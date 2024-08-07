package com.katorabian.compose_news.presentation.model

sealed class AuthState {

    data object Initial: AuthState()
    data object Authorized: AuthState()
    data object Unauthorized: AuthState()
}