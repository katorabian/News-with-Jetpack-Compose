package com.katorabian.mvidecomposetest.presentation_legacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katorabian.mvidecomposetest.data.RepositoryImpl
import com.katorabian.mvidecomposetest.domain.GetContactsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ContactListViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private val getContactsUseCase = GetContactsUseCase(repository)

    val contacts = getContactsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )
}
