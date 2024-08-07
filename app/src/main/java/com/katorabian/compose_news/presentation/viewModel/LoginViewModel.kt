package com.katorabian.compose_news.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.presentation.model.AuthState
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult

class LoginViewModel: ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        _authState.value = if (VK.isLoggedIn()) {
            AuthState.Authorized
        } else {
            AuthState.Unauthorized
        }
    }

    fun handleAuthResult(result: VKAuthenticationResult) {
        when (result) {
            is VKAuthenticationResult.Success -> {
                _authState.value = AuthState.Authorized
            }
            is VKAuthenticationResult.Failed -> {
                _authState.value = AuthState.Unauthorized
                Log.e(
                    VKAuthenticationResult::class.java.simpleName,
                    Log.getStackTraceString(result.exception)
                )
            }
        }
    }

}