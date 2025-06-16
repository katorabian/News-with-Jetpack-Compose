package com.katorabian.mvidecomposetest.presentation

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.katorabian.mvidecomposetest.data.RepositoryImpl
import com.katorabian.mvidecomposetest.domain.Contact
import com.katorabian.mvidecomposetest.domain.GetContactsUseCase
import com.katorabian.mvidecomposetest.presentation.ContactListStore.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ContactListStoreFactory {

    private val storeFactory: StoreFactory = LoggingStoreFactory(DefaultStoreFactory())
    private val repository = RepositoryImpl
    private val getContactsUseCase: GetContactsUseCase = GetContactsUseCase(repository)

    fun create(): ContactListStore = object : ContactListStore, Store<Intent, State, Label> by storeFactory.create(
        name = ContactListStore::class.java.simpleName,
        initialState = State(contactList = listOf()),
        bootstrapper = BootstrapperImpl(),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}.apply {
        Log.d("STORE_FACTORY", "CREATED ${this@ContactListStoreFactory::class.java.simpleName}")
    }


    private sealed interface Action {
        data class ContactsLoaded(val contacts: List<Contact>): Action
    }

    private sealed interface Msg {
        data class ContactsLoaded(val contacts: List<Contact>): Msg
    }


    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State {
            return when (msg) {
                is Msg.ContactsLoaded -> {
                    copy(contactList = msg.contacts)
                }
            }
        }
    }


    private inner class BootstrapperImpl: CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getContactsUseCase()
                    .onEach { contacts ->
                        dispatch(
                            Action.ContactsLoaded(contacts)
                        )
                    }.collect()
            }
        }
    }


    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.ContactsLoaded -> {
                    dispatch(Msg.ContactsLoaded(action.contacts))
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.AddContact -> {
                    publish(Label.AddContact)
                }
                is Intent.SelectContact -> {
                    publish(Label.EditContact(intent.contact))
                }
            }
        }
    }
}