package com.katorabian.mvidecomposetest.presentation

import com.katorabian.mvidecomposetest.domain.Contact
import kotlinx.coroutines.flow.StateFlow

interface ContactListComponent {

    val model: StateFlow<Model>

    fun onContactClicked(contact: Contact)
    fun onAddContactClicked()

    data class Model(
        val contactList: List<Contact>
    )
}