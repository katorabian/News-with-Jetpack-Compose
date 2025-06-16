package com.katorabian.mvidecomposetest.presentation

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.katorabian.mvidecomposetest.data.RepositoryImpl
import com.katorabian.mvidecomposetest.domain.AddContactUseCase
import com.katorabian.mvidecomposetest.presentation.AddContactStore.Intent
import com.katorabian.mvidecomposetest.presentation.AddContactStore.Label
import com.katorabian.mvidecomposetest.presentation.AddContactStore.State

class AddContactStoreFactory {

    private val storeFactory: StoreFactory = LoggingStoreFactory(DefaultStoreFactory())
    private val repository = RepositoryImpl
    private val addContactUseCase: AddContactUseCase = AddContactUseCase(repository)

    fun create(): AddContactStore =
        object : AddContactStore, Store<Intent, State, Label> by storeFactory.create(
            name = AddContactStore::class.java.simpleName,
            initialState = State(
                username = "",
                phone = ""
            ),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        ) {}.apply {
            Log.d("STORE_FACTORY", "CREATED ${this@AddContactStoreFactory::class.java.simpleName}")
        }

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUsername(val username: String): Msg
        data class ChangePhone(val phone: String): Msg
    }

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Label>(), Executor<Intent, Action, State, Msg, Label> {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangePhone -> {
                    dispatch(Msg.ChangePhone(phone = intent.phone))
                }
                is Intent.ChangeUsername -> {
                    dispatch(Msg.ChangeUsername(username = intent.username))
                }
                Intent.SaveContact -> {
                    val state = getState()
                    addContactUseCase(
                        username = state.username,
                        phone = state.phone
                    )
                    publish(Label.ContactSaved)
                }
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg) = when (msg) {
            is Msg.ChangePhone -> {
                copy(phone = msg.phone)
            }
            is Msg.ChangeUsername -> {
                copy(username = msg.username)
            }
        }
    }
}