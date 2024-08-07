package com.katorabian.compose_news.presentation.screen.main.models

sealed class AuthState {

    data object Initial: AuthState()
    data object Authorized: AuthState()
    data object Unauthorized: AuthState()
}