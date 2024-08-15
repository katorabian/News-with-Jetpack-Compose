package com.katorabian.compose_news.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.compose_news.domain.model.AuthState
import com.katorabian.compose_news.domain.usecase.GetAuthStateFlowUseCase
import com.katorabian.compose_news.domain.usecase.RefreshAuthStateUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    getAuthStateFlow: GetAuthStateFlowUseCase,
    private val refreshAuthState: RefreshAuthStateUseCase
): ViewModel() {

    val authState: StateFlow<AuthState> = getAuthStateFlow.get()

    fun handleAuthResult() {
        viewModelScope.launch {
            refreshAuthState.refresh()
        }
    }

}