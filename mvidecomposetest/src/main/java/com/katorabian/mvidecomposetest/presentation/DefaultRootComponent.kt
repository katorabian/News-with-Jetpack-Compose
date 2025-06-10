package com.katorabian.mvidecomposetest.presentation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.katorabian.mvidecomposetest.domain.Contact
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    fun child(
        componentContext: ComponentContext,
        config: Config
    ): ComponentContext {
        return when (config) {
            Config.AddContact -> {
                DefaultAddContactComponent(
                    componentContext = componentContext
                )
            }
            Config.ContactList -> {
                DefaultContactListComponent(
                    componentContext = componentContext,
                    onEditContactRequest = {},
                    onAddContactRequest = {}
                )
            }
            is Config.EditContact -> {
                DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = config.contact
                )
            }
        }
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