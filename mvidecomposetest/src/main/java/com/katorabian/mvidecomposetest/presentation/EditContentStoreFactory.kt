package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.katorabian.mvidecomposetest.presentation.EditContactStore.Intent
import com.katorabian.mvidecomposetest.presentation.EditContactStore.Label
import com.katorabian.mvidecomposetest.presentation.EditContactStore.State

class EditContentStoreFactory(
    private val storeFactory: StoreFactory
) {

    private val store: Store<Intent, State, Label> = storeFactory.create(
        name = EditContactStore::class.java.simpleName,
        initialState = State(
            username = "",
            phone = ""
        ),

    )

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeUsername(val username: String): Msg
        data class ChangePhone(val phone: String): Msg
    }

    private class ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg) = when (msg) {
            is Msg.ChangePhone -> {
                copy(phone = phone)
            }
            is Msg.ChangeUsername -> {
                copy(username = username)
            }
        }
    }
}