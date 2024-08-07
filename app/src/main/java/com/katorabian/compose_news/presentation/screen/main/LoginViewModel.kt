package com.katorabian.compose_news.presentation.screen.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.presentation.screen.main.models.AuthState
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class LoginViewModel(application: Application): AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val isLoggedIn = token != null && token.isValid

        _authState.value = if (isLoggedIn) {
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