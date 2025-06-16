package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
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

class DefaultEditContactComponent(
    componentContext: ComponentContext,
    contact: Contact,
    onContactSaved: () -> Unit
) : EditContactComponent, ComponentContext by componentContext {

    private val store: EditContactStore = instanceKeeper.getStore {
        val storeFactory = EditContentStoreFactory()
        storeFactory.create(contact)
    }
    private val coroutineScope by lazyComponentScope()

    init {
        coroutineScope.launch {
            store.labels.onEach {
                when (it) {
                    EditContactStore.Label.ContactSaved -> {
                        onContactSaved()
                    }
                }
            }.collect()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<EditContactComponent.Model>
        get() = store.stateFlow
            .map {
                EditContactComponent.Model(
                    username = it.username,
                    phone = it.phone
                )
            }.stateIn(
                scope = coroutineScope,
                started = SharingStarted.Lazily,
                EditContactComponent.Model("", "")
            )

    override fun onUsernameChanged(username: String) {
        store.accept(
            EditContactStore.Intent.ChangeUsername(username)
        )
    }

    override fun onPhoneChanged(phone: String) {
        store.accept(
            EditContactStore.Intent.ChangePhone(phone)
        )
    }

    override fun onSaveContactClicked() {
        store.accept(
            EditContactStore.Intent.SaveContact
        )
    }
}