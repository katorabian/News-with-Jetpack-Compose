package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.katorabian.mvidecomposetest.core.componentScope
import com.katorabian.mvidecomposetest.data.RepositoryImpl
import com.katorabian.mvidecomposetest.domain.Contact
import com.katorabian.mvidecomposetest.domain.GetContactsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultContactListComponent(
    componentContext: ComponentContext,
    val onEditContactRequest: (Contact) -> Unit,
    val onAddContactRequest: () -> Unit
) : ContactListComponent, ComponentContext by componentContext {

    private val repository = RepositoryImpl
    private val getContactsUseCase = GetContactsUseCase(repository)
    private val coroutineScope = componentScope()

    override val model: StateFlow<ContactListComponent.Model>
        get() = getContactsUseCase()
            .map { ContactListComponent.Model(it) }
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.Lazily,
                initialValue = ContactListComponent.Model(listOf())
            )

    override fun onContactClicked(contact: Contact) {
        onEditContactRequest(contact)
    }

    override fun onAddContactClicked() {
        onAddContactRequest()
    }
}