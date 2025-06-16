package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.katorabian.mvidecomposetest.domain.Contact
import com.katorabian.mvidecomposetest.domain.EditContactUseCase
import com.katorabian.mvidecomposetest.presentation.EditContactStore.Intent
import com.katorabian.mvidecomposetest.presentation.EditContactStore.Label
import com.katorabian.mvidecomposetest.presentation.EditContactStore.State

class EditContentStoreFactory(
    private val storeFactory: StoreFactory,
    private val editContactUseCase: EditContactUseCase
) {

    fun create(contact: Contact): EditContactStore =
        object : EditContactStore, Store<Intent, State, Label> by storeFactory.create(
            name = EditContactStore::class.java.simpleName,
            initialState = State(
                id = contact.id,
                username = contact.username,
                phone = contact.phone
            ),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUsername(val username: String): Msg
        data class ChangePhone(val phone: String): Msg
    }

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Label>() {

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
                    val contact = Contact(
                        id = state.id,
                        username = state.username,
                        phone = state.phone
                    )
                    editContactUseCase(contact = contact)
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