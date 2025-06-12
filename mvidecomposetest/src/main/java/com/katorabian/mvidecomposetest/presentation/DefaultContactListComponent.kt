package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.katorabian.mvidecomposetest.core.lazyComponentScope
import com.katorabian.mvidecomposetest.domain.Contact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultContactListComponent(
    componentContext: ComponentContext,
    val onEditContactRequest: (Contact) -> Unit,
    val onAddContactRequest: () -> Unit
) : ContactListComponent, ComponentContext by componentContext {

    private val coroutineScope by lazyComponentScope()

    lateinit var store: ContactListStore

    init {
        coroutineScope.launch {
            store.labels.onEach {
                when (it) {
                    ContactListStore.Label.AddContact -> {
                        onAddContactRequest()
                    }
                    is ContactListStore.Label.EditContact -> {
                        onEditContactRequest(it.contact)
                    }
                }
            }.collect()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ContactListComponent.Model>
        get() = store.stateFlow
            .map { ContactListComponent.Model(it.contactList) }
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.Lazily,
                initialValue = ContactListComponent.Model(listOf())
            )

    override fun onContactClicked(contact: Contact) {
        store.accept(
            ContactListStore.Intent.SelectContact(contact)
        )
    }

    override fun onAddContactClicked() {
        store.accept(
            ContactListStore.Intent.AddContact
        )
    }
}