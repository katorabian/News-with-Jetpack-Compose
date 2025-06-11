package com.katorabian.mvidecomposetest.presentation

import android.os.Parcelable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.katorabian.mvidecomposetest.domain.Contact
import kotlinx.parcelize.Parcelize

interface RootComponent {

    val stack: Value<ChildStack<Config, Child>>

    sealed interface Child {
        class AddContact(val component: AddContactComponent): Child
        class ContactList(val component: ContactListComponent): Child
        class EditContact(val component: EditContactComponent): Child
    }

    sealed interface Config: Parcelable {
        @Parcelize
        data object ContactList: Config
        @Parcelize
        data object AddContact: Config
        @Parcelize
        data class EditContact(val contact: Contact): Config
    }
}