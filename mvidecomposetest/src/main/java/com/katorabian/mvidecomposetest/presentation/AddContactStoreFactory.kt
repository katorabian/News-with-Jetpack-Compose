package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.katorabian.mvidecomposetest.presentation.AddContactStore.Intent
import com.katorabian.mvidecomposetest.presentation.AddContactStore.Label
import com.katorabian.mvidecomposetest.presentation.AddContactStore.State

class AddContactStoreFactory(
    private val storeFactory: StoreFactory
) {
    private val store: Store<Intent, State, Label> = storeFactory.create(
        name = AddContactStore::class.java.simpleName,
        initialState = AddContactStore.State(
            username = "",
            phone = ""
        ),

    )

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUsername(val username: String): Msg
        data class ChangePhone(val phone: String): Msg
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