package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.katorabian.mvidecomposetest.domain.AddContactUseCase
import com.katorabian.mvidecomposetest.presentation.AddContactStore.Intent
import com.katorabian.mvidecomposetest.presentation.AddContactStore.Label
import com.katorabian.mvidecomposetest.presentation.AddContactStore.State

class AddContactStoreFactory(
    private val storeFactory: StoreFactory,
    private val addContactUseCase: AddContactUseCase
) {
    private val store: Store<Intent, State, Label> = storeFactory.create(
        name = AddContactStore::class.java.simpleName,
        initialState = AddContactStore.State(
            username = "",
            phone = ""
        ),
        reducer = ReducerImpl,
        executorFactory = ::ExecutorImpl
    )

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