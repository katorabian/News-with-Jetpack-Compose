package com.katorabian.compose_news.data.repository

import com.katorabian.compose_news.domain.model.AuthState
import com.katorabian.compose_news.domain.repository.AuthRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val storage: VKPreferencesKeyValueStorage
): AuthRepository {

    private val token: VKAccessToken?
        get() = VKAccessToken.restore(storage)

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)
    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currToken = token
            val isLoggedIn = currToken?.isValid == true
            val authState =
                if (isLoggedIn) AuthState.Authorized
                else AuthState.Unauthorized

            emit(authState)
        }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override suspend fun refreshAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

}