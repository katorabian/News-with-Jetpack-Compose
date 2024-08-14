package com.katorabian.compose_news.presentation.screen.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.data.repository.AuthRepository
import com.katorabian.compose_news.domain.model.AuthState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): AndroidViewModel(application) {

    private val repository = AuthRepository(application = application)

    val authState: StateFlow<AuthState> = repository.authStateFlow

    fun handleAuthResult() {
        viewModelScope.launch {
            repository.checkAuthState()
        }
    }

}