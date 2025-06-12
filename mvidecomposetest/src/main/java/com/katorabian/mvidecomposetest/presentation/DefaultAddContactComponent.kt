package com.katorabian.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.katorabian.mvidecomposetest.core.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultAddContactComponent(
    componentContext: ComponentContext,
    onContactSaved: () -> Unit
) : AddContactComponent, ComponentContext by componentContext {

    private val coroutineScope by lazy { componentScope() }
    private lateinit var store: AddContactStore

    init {
        coroutineScope.launch {
            store.labels.onEach {
                when (it) {
                    AddContactStore.Label.ContactSaved -> {
                        onContactSaved()
                    }
                }
            }.collect()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<AddContactComponent.Model>
        get() = store.stateFlow
            .map {
                AddContactComponent.Model(
                    username = it.username,
                    phone = it.phone
                )
            }.stateIn(
                scope = coroutineScope,
                started = SharingStarted.Lazily,
                AddContactComponent.Model("", "")
            )

    override fun onUsernameChanged(username: String) {
        store.accept(
            AddContactStore.Intent.ChangeUsername(username)
        )
    }

    override fun onPhoneChanged(phone: String) {
        store.accept(
            AddContactStore.Intent.ChangePhone(phone)
        )
    }

    override fun onSaveContactClicked() {
        store.accept(
            AddContactStore.Intent.SaveContact
        )
    }

    companion object {
        private const val KEY = "DefaultAddContactComponent"
    }
}