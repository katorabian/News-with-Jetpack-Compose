package com.katorabian.mvidecomposetest.presentation

import com.katorabian.mvidecomposetest.data.RepositoryImpl
import com.katorabian.mvidecomposetest.domain.Contact
import com.katorabian.mvidecomposetest.domain.GetContactsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultContactListComponent(
    val onEditContactRequest: (Contact) -> Unit,
    val onAddContactRequest: () -> Unit
) : ContactListComponent {

    private val repository = RepositoryImpl
    private val getContactsUseCase = GetContactsUseCase(repository)
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

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