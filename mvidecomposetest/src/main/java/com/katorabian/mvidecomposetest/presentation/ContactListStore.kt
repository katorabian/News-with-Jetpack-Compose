package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Store
import com.katorabian.mvidecomposetest.domain.Contact
import com.katorabian.mvidecomposetest.presentation.ContactListStore.*

interface ContactListStore: Store<Intent, State, Label> {

    data class State(
        val contactList: List<Contact>
    )

    sealed interface Intent {
        data class SelectContact(val contact: Contact): Intent
        data object AddContact : Intent
    }

    sealed interface Label {
        data class EditContact(val contact: Contact): Label
        data object AddContact: Label
    }
}