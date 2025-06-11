package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootComponent.Config>()
    override val stack: Value<ChildStack<RootComponent.Config, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = RootComponent.Config.ContactList,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: RootComponent.Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            RootComponent.Config.AddContact -> {
                val component = DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.AddContact(component)
            }
            RootComponent.Config.ContactList -> {
                val component = DefaultContactListComponent(
                    componentContext = componentContext,
                    onAddContactRequest = {
                        navigation.push(RootComponent.Config.AddContact)
                    },
                    onEditContactRequest = { contact ->
                        navigation.push(
                            RootComponent.Config.EditContact(contact = contact)
                        )
                    }
                )
                RootComponent.Child.ContactList(component)
            }
            is RootComponent.Config.EditContact -> {
                val component = DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = config.contact,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.EditContact(component)
            }
        }
    }
}