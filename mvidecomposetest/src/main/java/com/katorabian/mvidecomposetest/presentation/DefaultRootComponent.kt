package com.katorabian.mvidecomposetest.presentation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.katorabian.mvidecomposetest.domain.Contact
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.ContactList,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): Child {
        return when (config) {
            Config.AddContact -> {
                val component = DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                Child.AddContact(component)
            }
            Config.ContactList -> {
                val component = DefaultContactListComponent(
                    componentContext = componentContext,
                    onAddContactRequest = {
                        navigation.push(Config.AddContact)
                    },
                    onEditContactRequest = { contact ->
                        navigation.push(
                            Config.EditContact(contact = contact)
                        )
                    }
                )
                Child.ContactList(component)
            }
            is Config.EditContact -> {
                val component = DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = config.contact,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                Child.EditContact(component)
            }
        }
    }

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