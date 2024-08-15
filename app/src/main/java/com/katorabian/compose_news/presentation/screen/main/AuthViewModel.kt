package com.katorabian.compose_news.presentation.screen.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.data.repository.AuthRepositoryImpl
import com.katorabian.compose_news.domain.model.AuthState
import com.katorabian.compose_news.domain.usecase.GetAuthStateFlowUseCase
import com.katorabian.compose_news.domain.usecase.RefreshAuthStateUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): AndroidViewModel(application) {

    private val repository = AuthRepositoryImpl(application = application)
    private val getAuthStateFlow = GetAuthStateFlowUseCase(repository)
    private val refreshAuthState = RefreshAuthStateUseCase(repository)

    val authState: StateFlow<AuthState> = getAuthStateFlow.get()

    fun handleAuthResult() {
        viewModelScope.launch {
            refreshAuthState.refresh()
        }
    }

}